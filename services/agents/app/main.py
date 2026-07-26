from fastapi import FastAPI, HTTPException

from agent.router import analytics_agent_router

app = FastAPI(
    title="Data Science & Analyst AI Agent",
    description="An advanced multi-format data agent built with FastAPI, LangGraph, and Gemini 2.5 Flash.",
    version="1.0.0",
)


@app.get("/healtz")
def health():
    return {"status": "ok"}


@app.get("/error")
def error():
    raise HTTPException(status_code=404, detail="Error endpoint, Blahhhhh....")


app.include_router(analytics_agent_router, prefix="/api/v1")
