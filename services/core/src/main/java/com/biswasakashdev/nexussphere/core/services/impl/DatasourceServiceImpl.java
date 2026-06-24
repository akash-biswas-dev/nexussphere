package com.biswasakashdev.nexussphere.core.services.impl;


import com.biswasakashdev.nexussphere.core.dtos.requests.*;
import com.biswasakashdev.nexussphere.core.models.DataSource;
import com.biswasakashdev.nexussphere.core.models.RepositorySource;
import com.biswasakashdev.nexussphere.core.models.TabularSource;
import com.biswasakashdev.nexussphere.core.models.UnstructuredSource;
import com.biswasakashdev.nexussphere.core.repository.impl.PostgresDataSourceRepositoryImpl;
import com.biswasakashdev.nexussphere.core.services.DataSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class DatasourceServiceImpl implements DataSourceService {


    private final PostgresDataSourceRepositoryImpl dataSourceRepository;


    public Mono<Void> saveDatasource(DatasourceRequest request) {
        DataSourceBaseDetails baseDetails = request.getBaseDetails();
        DataSource newDataSource = DataSource
                .builder()
                .name(baseDetails.name())
                .description(baseDetails.description())
                .dataType(request.getType().name())
                .build();

        return dataSourceRepository
                .createDataSource(newDataSource)
                .flatMap(saveDatasource -> switch (request) {
                    case RepositoryDatasourceRequest repository -> dataSourceRepository
                            .createRepositorySource(
                                    RepositorySource
                                            .builder()
                                            .dataSourceId(saveDatasource.getId())
                                            .url(repository.url())
                                            .username(repository.username())
                                            .password(repository.password())
                                            .build()
                            )
                            .then();
                    case TabularDatasourceRequest tabular -> {
                        // TODO: Save the file at storage.
                        String fileId = "a-long-file-id";
                        yield  dataSourceRepository
                                .createTabularSource(
                                        TabularSource
                                                .builder()
                                                .dataSourceId(saveDatasource.getId())
                                                .fileId(fileId)
                                                .build()
                                ).then();
                    }
                    case UnstructuredDatasourceRequest unstructured -> {
//                        TODO: Upload the bytes to the storage.
                        String fileId = "a-long-file-id";
                        yield   dataSourceRepository
                                .createUnstructuredSource(
                                        UnstructuredSource.builder()
                                                .dataSourceId(saveDatasource.getId())
                                                .fileId(fileId)
                                                .build()
                                )
                                .then();
                    }
                });

    }
}
