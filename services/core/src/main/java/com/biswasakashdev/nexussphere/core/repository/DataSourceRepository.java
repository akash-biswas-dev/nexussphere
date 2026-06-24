package com.biswasakashdev.nexussphere.core.repository;

import com.biswasakashdev.nexussphere.core.models.DataSource;
import com.biswasakashdev.nexussphere.core.models.RepositorySource;
import com.biswasakashdev.nexussphere.core.models.TabularSource;
import com.biswasakashdev.nexussphere.core.models.UnstructuredSource;
import reactor.core.publisher.Mono;

public interface DataSourceRepository {

    Mono<DataSource> createDataSource(DataSource dataSource);

    Mono<TabularSource> createTabularSource(TabularSource tabularSource);

    Mono<UnstructuredSource> createUnstructuredSource(UnstructuredSource unstructuredSource);

    Mono<RepositorySource> createRepositorySource(RepositorySource repositorySource);

}
