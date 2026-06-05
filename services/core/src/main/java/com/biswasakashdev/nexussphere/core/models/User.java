package com.biswasakashdev.nexussphere.core.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table("nex_users")
public class User {
    @Id
    private String id;
    private String email;
    private String password;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    private String gender;
    @Column("created_on")
    private LocalDate createdOn;
    @Column("account_locked")
    private Boolean accountLocked;
}
