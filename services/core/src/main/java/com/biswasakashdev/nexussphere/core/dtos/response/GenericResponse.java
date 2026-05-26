package com.biswasakashdev.nexussphere.core.dtos.response;


public record GenericResponse<T>(
        ResponseCode code,
        String message,
        T data
) {
}
