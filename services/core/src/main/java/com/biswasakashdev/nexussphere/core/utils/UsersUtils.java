package com.biswasakashdev.nexussphere.core.utils;

import com.biswasakashdev.nexussphere.core.dtos.response.UserResponse;
import com.biswasakashdev.nexussphere.core.dtos.response.UserProfileResponse;
import com.biswasakashdev.nexussphere.core.models.User;

public class UsersUtils {

    public static UserResponse getUserResponse(User user) {
        return new UserResponse(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public static UserProfileResponse getUserProfileResponse(User user) {
        return new UserProfileResponse(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getGender()
        );
    }
}
