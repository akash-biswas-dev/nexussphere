package com.biswasakashdev.nexussphere.core.dtos.response;


import lombok.Builder;

@Builder
public record UserProfileResponse(
        String email,
        String firstName,
        String lastName,
        String gender
) {
}
