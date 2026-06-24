from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from typing import ClassVar as _ClassVar, Optional as _Optional

DESCRIPTOR: _descriptor.FileDescriptor

class GiveDataInsightsRequest(_message.Message):
    __slots__ = ("data_source_id",)
    DATA_SOURCE_ID_FIELD_NUMBER: _ClassVar[int]
    data_source_id: str
    def __init__(self, data_source_id: _Optional[str] = ...) -> None: ...

class GiveDataInsightsResponse(_message.Message):
    __slots__ = ("resp_message",)
    RESP_MESSAGE_FIELD_NUMBER: _ClassVar[int]
    resp_message: str
    def __init__(self, resp_message: _Optional[str] = ...) -> None: ...
