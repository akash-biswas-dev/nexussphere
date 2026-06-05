package com.biswasakashdev.nexussphere.core.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table("data_source")
public class DataSource {

    @Id
    private String id;
    private  String name;
    private String description;
    @Column(value = "data_type")
    private String dataType;
    private String workspaceId;

}
