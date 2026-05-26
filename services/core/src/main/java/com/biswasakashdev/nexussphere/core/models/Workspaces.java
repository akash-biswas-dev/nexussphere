package com.biswasakashdev.nexussphere.core.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "workspaces")
public class Workspaces {

    @Id
    private String id;
    @Column("workspace_name")
    private String name;
    @Column("owner_id")
    private String ownedId;
    @Column("created_on")
    private LocalDate createdOn;

}

