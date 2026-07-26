# from model import gemini_model

from fastapi import APIRouter, Path
from langchain.messages import HumanMessage
from langchain_core.prompts import ChatPromptTemplate

from agent.analyzer import AnalyzerAgentState, analyzer_agent

analytics_agent_router = APIRouter(prefix="/analytics")


# Endpoint which analyze the data with multiple aspect
# And also what information can get with first impression.
@analytics_agent_router.get("/{data_id}")
def analyze(data_id: str = Path(..., description="Data source id.")):
    # Send a small sample of data and understand what is the data about then generate some questions
    #
    return {"message": "The data is about a business sales."}
