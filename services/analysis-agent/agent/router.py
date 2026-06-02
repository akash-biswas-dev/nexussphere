# from model import gemini_model
from fastapi import APIRouter, Path

analytics_router = APIRouter(prefix="/analytics")


# Endpoint which analyze the data with multiple aspect
# And also what information can get with first impression.
@analytics_router.get("/{data_id}")
def analyze(data_id: str = Path(..., description="Data source id.")):
    # Send a small sample of data and understand what is the data about then generate some questions
    # What can do with this data.
    # Analyze the data quality.
    # if it's a unstrctured data then How to structure that.
    print(data_id)
    return {"message": "The data is about a business sales."}
