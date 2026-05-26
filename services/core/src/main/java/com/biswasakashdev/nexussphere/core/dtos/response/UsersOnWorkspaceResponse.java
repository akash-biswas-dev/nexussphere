package com.biswasakashdev.nexussphere.core.dtos.response;

import java.time.LocalDateTime;

public record UsersOnWorkspaceResponse(
        String userId,
        String firstName,
        String lastName,
        String username,
        LocalDateTime lastAccessed,
        Boolean isActivate
) {
}
