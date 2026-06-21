from typing import Any, Dict, List, Literal, TypedDict

from langchain_core.messages import BaseMessage
from langgraph.graph import END, StateGraph
from model import gemini_model

from tools.data_processor import data_processor_tool, database_fetcher_tool
from tools.ui_generator import component_generator

# Bind the tools explicitly to the Gemini LLM instance
tools = [data_processor_tool, component_generator, database_fetcher_tool]
llm_with_tools = gemini_model.bind_tools(tools)


# ==========================================
# 1. STATE DEFINITION
# ==========================================
class AgentState(TypedDict):
    """Tracks the conversational state and execution context of the agent."""

    messages: List[BaseMessage]
    # Internal metadata tracking data schemas, temporary states, or human-in-the-loop flags
    context: Dict[str, Any]


def call_model(state: AgentState):
    pass


def tool_node(state: AgentState):
    pass


def route_conditional(
    state: AgentState,
) -> Literal["execute_tools", "human_intervention", "__end__"]:
    """Determines whether the graph should branch into tool execution, wait for a user, or finish."""
    last_message = state["messages"][-1]

    if not last_message.tool_calls:
        # Check if the model is explicitly seeking user clarification/feedback regarding data actions
        if (
            "null values" in last_message.content.lower()
            or "should i" in last_message.content.lower()
        ):
            return "human_intervention"
        return END

    return "execute_tools"


# ==========================================
# 4. GRAPH ASSEMBLY
# ==========================================
workflow = StateGraph(AgentState)

# Define Nodes
workflow.add_node("agent_core", call_model)
workflow.add_node("execute_tools", tool_node)

# Set Entry Point
workflow.set_entry_point("agent_core")

# Define Transitions
workflow.add_conditional_edges(
    "agent_core",
    route_conditional,
    {
        "execute_tools": "execute_tools",
        "human_intervention": END,  # Pause execution and return control back to the client api
        END: END,
    },
)
# Loop back after processing tools for iterative multi-step optimization
workflow.add_edge("execute_tools", "agent_core")

graph_agent = workflow.compile()
