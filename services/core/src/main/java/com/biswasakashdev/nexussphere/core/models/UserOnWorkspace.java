package com.biswasakashdev.nexussphere.core.models;


import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users_on_workspaces")
public class UserOnWorkspace {

    private String userId;
    private String workspaceId;
    private LocalDate joinedOn;
    private LocalDateTime lastActive;
    private Boolean writePermission;

}
