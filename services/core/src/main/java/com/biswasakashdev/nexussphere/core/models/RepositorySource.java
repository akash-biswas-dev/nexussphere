package com.biswasakashdev.nexussphere.core.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table("repository_sources")
public class RepositorySource extends DataSource {

    @Id
    private String dataSourceId;
    private RepositoryType repositoryType;
    private String url;
    private String username;
    private String password;
}
