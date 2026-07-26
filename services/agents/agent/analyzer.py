from typing import Annotated, List, Literal, TypedDict, cast

from langchain_core.messages import AIMessage, BaseMessage
from langchain_core.prompts import ChatPromptTemplate
from langgraph.graph import END, StateGraph
from langgraph.graph.message import add_messages
from langgraph.prebuilt import ToolNode

from tools.statistical import StatisticalTool

from .model import gemini_model

# Bind the tools explicitly to the Gemini LLM instance
statistical_tool = StatisticalTool()
tools = [statistical_tool]
llm_with_tools = gemini_model.bind_tools(tools)


# ==========================================
# 1. STATE DEFINITION
# ==========================================
class AnalyzerAgentState(TypedDict):
    """Tracks the conversational state and execution context of the agent."""

    loop_times: int
    data_source_id: str
    messages: Annotated[List[BaseMessage], add_messages]


def prompt_setup(state: AnalyzerAgentState):

    data_source_id = state["data_source_id"]

    prompt_template = ChatPromptTemplate(
        [
            (
                "system",
                """
            You are a deterministic Data Analysis Agent. Your sole objective is to analyze tabular data using provided tools. You must always use the exact `data_source_id` provided in the User Request for every tool execution.

            ### OPERATIONAL RULES
            1. DATA DISCOVERY: Always begin by calling `shape_tool` and `sample_tool` to understand the structural context and data types before executing advanced metrics.
            2. TOOL EXECUTION: Never hallucinate statistical outcomes. If you need data insights (e.g., missing values, means, ranges), you MUST call the appropriate tool.
            3. CONSTRAINTS: Only analyze columns explicitly present in the data sample. If a requested column is missing, report it immediately without calling further tools.
            4. PARALLELIZATION: Combine independent metric requests into a single optimized tool call where supported (e.g., requesting `mean` and `null_distribution` together in `statistical_tool`).

            ### THOUGHT PROCESS (LOOP)
            For every turn, explicitly follow this sequence:
            - Thought: State what information is missing and which tool is required to fetch it.
            - Action: Invoke the tool using the precise `data_source_id`.
            - Observation: Analyze the structured output from the tool.

            ### OUTPUT FORMAT
            Provide your final answer using this markdown layout:
            ## Data Analysis Summary
            [High-level overview of the data based on tools]
            ## Insights & Metrics
            [Bullet points showing the exact values returned by the tools]
        """,
            ),
            ("human", "The data_source_id is: {data_source_id}"),
        ]
    )

    prompt = prompt_template.invoke({"data_source_id": data_source_id})

    return {"messages": prompt.to_messages()}


def call_model(state: AnalyzerAgentState):

    messages = state["messages"]
    curr_loop = state["loop_times"]

    llm_response = llm_with_tools.invoke(messages)

    print(llm_response)

    return {"messages": [llm_response], "loop_times": curr_loop - 1}


def route_conditional(
    state: AnalyzerAgentState,
) -> Literal["execute_tools", "human_intervention", "__end__"]:
    """Determines whether the graph should branch into tool execution, wait for a user, or finish."""

    if state["loop_times"] == 3:
        return "__end__"

    last_message = state["messages"][-1]

    llm_message = cast(AIMessage, last_message)

    if not llm_message.tool_calls:
        # Check if the model is explicitly seeking user clarification/feedback regarding data actions
        last_message_content = cast(str, last_message.content)
        if (
            "null values" in last_message_content.lower()
            or "should i" in last_message_content.lower()
        ):
            return "human_intervention"
        return "__end__"

    return "execute_tools"


# ==========================================
# 4. GRAPH ASSEMBLY
# ==========================================
workflow = StateGraph(AnalyzerAgentState)

tools_node = ToolNode(tools)

# Define Nodes
workflow.add_node("prompt_setup", prompt_setup)
workflow.add_node("llm_call", call_model)
workflow.add_node("execute_tools", tools_node)

# Set Entry Point
workflow.set_entry_point("prompt_setup")

# Define Edges

workflow.add_edge("prompt_setup", "llm_call")
workflow.add_conditional_edges(
    "llm_call",
    route_conditional,
    {
        "execute_tools": "execute_tools",
        "human_intervention": END,  # Pause execution and return control back to the client api
        "__end__": END,
    },
)
workflow.add_edge("execute_tools", "llm_call")

# Compile the graph
analyzer_agent = workflow.compile()
