package com.biswasakashdev.nexussphere.core.dtos.response;

import java.time.LocalDateTime;

public record UsersOnWorkspaceDTO(
        String userId,
        String firstName,
        String lastName,
        Boolean writePermission,
        LocalDateTime lastActive
){
}
