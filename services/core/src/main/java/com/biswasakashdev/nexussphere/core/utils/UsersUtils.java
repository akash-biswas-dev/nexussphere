package com.biswasakashdev.nexussphere.core.utils;

import com.biswasakashdev.nexussphere.core.dtos.response.UserResponse;
import com.biswasakashdev.nexussphere.core.dtos.response.UserProfileResponse;
import com.biswasakashdev.nexussphere.core.models.Users;

public class UsersUtils {

    public static UserResponse getUserResponse(Users users) {
        return new UserResponse(
                users.getEmail(),
                users.getFirstName(),
                users.getLastName()
        );
    }

    public static UserProfileResponse getUserProfileResponse(Users users) {
        return new UserProfileResponse(
                users.getEmail(),
                users.getFirstName(),
                users.getLastName(),
                users.getGender()
        );
    }
}
