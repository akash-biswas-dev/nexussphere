from typing import Literal, Type

from langchain.tools import BaseTool
from pydantic import BaseModel, Field

DataSamplePostion = Literal["head", "tail"]


class DataShapeToolInputSchema(BaseModel):
    data_source_id: str = Field(
        description="The data source id where the operations are made."
    )


class DataShapeTool(BaseTool):
    name: str = "data_shape_tool"
    description: str = """
    Shapes or dimentions of data for the given datasource.
    Like 5 * 100 where 100 is the number of rows and 5 is the number of features.
    """
    args_schema: Type[BaseModel] = DataShapeToolInputSchema

    def _run(self, data_source_id: str) -> str:
        # synchronous execution
        return f"Shaping data from datasource {data_source_id}"

    async def _arun(self, data_source_id: str) -> str:
        # asynchronous execution
        return f"Shaping data asynchronously from datasource {data_source_id}"


class DataSampleToolInputSchema(BaseModel):
    data_source_id: str = Field(
        description="The data source id where the operations are made."
    )
    position: DataSamplePostion = Field(
        default="head", description="Defines data sample position."
    )
    sample_size: int = Field(
        default=5, le=5, ge=51, description="The sample size of the data"
    )


class DataSampleTool(BaseTool):
    name = "data_sample_tool"
    description: str = """
    Data sample can give the smaple of the given data set like 5 or 10 samples with the features name
    in json list of object string.
    [
        {
            "feature-1":"2345$",
            "feature-2":"5678"
        },
        {
            "feature-1":"2345$",
            "feature-2":"Nan" // Null value
        }
    ]
    """
    args_schema: Type[BaseModel] = DataSampleToolInputSchema

    def _run(self, data_source_id: str, position: str, sample_size: int) -> str:
        return f"Sampling {sample_size} rows from {position} of datasource {data_source_id}"

    async def _arun(self, data_source_id: str, position: str, sample_size: int) -> str:
        return f"Sampling {sample_size} rows asynchronously from {position} of datasource {data_source_id}"


sample_tool = DataSampleTool()
shape_tool = DataShapeTool()


class DataToolsKit:
    def get_tools(self):
        return [sample_tool, shape_tool]
