from fastapi import FastAPI, HTTPException

from agent.router import analytics_router

# model = ChatGoogleGenerativeAI(model="gemini-2.5-flash")

app = FastAPI(title="Ai analytics engine")


@app.get("/healtz")
def health():
    return {"status": "ok"}


@app.get("/error")
def error():
    raise HTTPException(status_code=404, detail="Error endpoint, Blahhhhh....")


app.include_router(analytics_router, prefix="/api/v1")
