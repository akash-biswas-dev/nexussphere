package com.biswasakashdev.nexussphere.core.dtos.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record WorkspaceResponse(
        String id,
        String name,
        String ownedBy,
        LocalDateTime lastActive,
        LocalDate joinedOn,
        Long userCount,
        Long pageCount
) {
}
