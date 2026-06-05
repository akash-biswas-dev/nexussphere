package com.biswasakashdev.nexussphere.core.repository.impl;

import com.biswasakashdev.nexussphere.core.models.DataSource;
import com.biswasakashdev.nexussphere.core.models.RepositorySource;
import com.biswasakashdev.nexussphere.core.models.TabularSource;
import com.biswasakashdev.nexussphere.core.models.UnstructuredSource;
import com.biswasakashdev.nexussphere.core.repository.DataSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
@RequiredArgsConstructor
public class PostgresDataSourceRepositoryImpl implements DataSourceRepository {

    private final R2dbcEntityTemplate template;

    @Override
    public Mono<DataSource> createDataSource(DataSource dataSource) {
        return template
                .insert(DataSource.class)
                .using(dataSource);
    }

    @Override
    public Mono<TabularSource> createTabularSource(TabularSource tabularSource) {
        return template
                .insert(TabularSource.class)
                .using(tabularSource);
    }

    @Override
    public Mono<UnstructuredSource> createUnstructuredSource(UnstructuredSource unstructuredSource) {
        return template
                .insert(UnstructuredSource.class)
                .using(unstructuredSource);
    }

    @Override
    public Mono<RepositorySource> createRepositorySource(RepositorySource repositorySource) {
        return template
                .insert(RepositorySource.class)
                .using(repositorySource);
    }
}
