from agent.analyzer import AnalyzerAgentState, analyzer_agent

initial_state: AnalyzerAgentState = {
    "loop_times": 3,
    "data_source_id": "acsbBCJKSkjbASKJBk",
    "messages": [],
}
state = analyzer_agent.invoke(initial_state)

print(state)
