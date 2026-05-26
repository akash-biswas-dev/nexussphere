package com.biswasakashdev.nexussphere.core.dtos.requests;

import com.biswasakashdev.nexussphere.core.models.Gender;

public record NewUserRequest(
        String email,
        String password,
        String firstName,
        String lastName,
        String gender
) {
}
