package com.biswasakashdev.nexussphere.core.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pages")
public class Pages {

    @Id
    private String id;

    private String name;

    private String workspaceId;
}
