# from model import gemini_model
from typing import Any, Dict, List

from fastapi import APIRouter, HTTPException, Path
from langchain_core.messages import AIMessage, HumanMessage
from pydantic import BaseModel

from agent.graph import AgentState, graph_agent

analytics_agent_router = APIRouter(prefix="/analytics")


# Endpoint which analyze the data with multiple aspect
# And also what information can get with first impression.
@analytics_agent_router.get("/{data_id}")
def analyze(data_id: str = Path(..., description="Data source id.")):
    # Send a small sample of data and understand what is the data about then generate some questions
    # What can do with this data.
    # Analyze the data quality.
    # if it's a unstrctured data then How to structure that.
    print(data_id)
    return {"message": "The data is about a business sales."}


# ==========================================
# 5. FASTAPI ROUTE HANDLING
# ==========================================
class AgentRequest(BaseModel):
    user_input: str
    history: List[Dict[str, str]] = []
    context: Dict[str, Any] = {}


class AgentResponse(BaseModel):
    response: str
    history: List[Dict[str, Any]]
    context: Dict[str, Any]
    requires_action: bool = False


@analytics_agent_router.post("/api/v1/agent/chat", response_model=AgentResponse)
async def chat_with_agent(payload: AgentRequest):
    """
    Handles conversation streams with the Data Science Agent.
    Maintains stateless API execution by processing incoming historical payloads.
    """
    try:
        # Format incoming history into LangChain message structures
        formatted_messages = []
        for msg in payload.history:
            if msg["role"] == "user":
                formatted_messages.append(HumanMessage(content=msg["content"]))
            elif msg["role"] == "assistant":
                formatted_messages.append(AIMessage(content=msg["content"]))

        # Append latest query
        formatted_messages.append(HumanMessage(content=payload.user_input))

        # Initialize internal graph execution state
        initial_state: AgentState = {
            "messages": formatted_messages,
            "context": payload.context,
        }

        # Invoke the compiled LangGraph agent execution loop
        final_output = graph_agent.invoke(initial_state)

        # Process output tracking
        final_messages = final_output["messages"]
        last_ai_message = final_messages[-1]

        # Check if the final node hit a human confirmation point
        requires_action = "null values" in last_ai_message.content.lower()

        # Reconstruct standard history payload for simple frontend consumption
        serialized_history = []
        for msg in final_messages:
            # Skip systemic baseline prompts to clear pipeline bloat
            if (
                isinstance(msg, AIMessage)
                and "You are an expert Data Science Engineer" in msg.content
            ):
                continue
            role = "user" if isinstance(msg, HumanMessage) else "assistant"
            serialized_history.append({"role": role, "content": msg.content})

        return AgentResponse(
            response=last_ai_message.content,
            history=serialized_history,
            context=final_output.get("context", {}),
            requires_action=requires_action,
        )

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
