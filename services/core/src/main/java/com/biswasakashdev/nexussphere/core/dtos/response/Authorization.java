package com.biswasakashdev.nexussphere.core.dtos.response;

public record Authorization(
        String token,
        UserResponse user
) {
}
