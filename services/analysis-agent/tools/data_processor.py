from typing import Any, Dict, Literal


def data_processor_tool(
    operation: str, sample_size: int = 5, params: Dict[str, Any] = None
) -> Dict[str, Any]:
    """
    Executes core statistical calculations, mutations, updates, or checks data insights.

    TOOL INPUT SPECIFICATION (What the agent passes):
    {
        "operation": "null_distribution" | "mean" | "median" | "std_dev" | "scale" | "update_field",
        "sample_size": 5,
        "params": {"columns": ["sales", "revenue"], "method": "minmax"}
    }

    TOOL OUTPUT SPECIFICATION (What the tool returns to the agent):
    {
        "status": "success",
        "insights": {"null_counts": {"sales": 0, "date": 12}},
        "data_preview": [{"date": "2026-06-01", "sales": 150.0}]
    }
    """
    pass


def database_fetcher_tool(
    source_type: Literal["SQL", "NoSQL", "Logs"], query_or_filter: str
) -> Dict[str, Any]:
    """
    Fetches raw/structured datasets from SQL, MongoDB, or unstructured server logs.

    TOOL INPUT SPECIFICATION:
    {
        "source_type": "SQL",
        "query_or_filter": "SELECT * FROM transactions WHERE date BETWEEN '2026-06-01' AND '2026-07-31'"
    }

    TOOL OUTPUT SPECIFICATION:
    {
        "schema": {"transaction_id": "INT", "date": "DATE", "amount": "FLOAT"},
        "row_count": 1450,
        "data_preview": [{"transaction_id": 1, "date": "2026-06-15", "amount": 250.5}]
    }
    """
    pass
