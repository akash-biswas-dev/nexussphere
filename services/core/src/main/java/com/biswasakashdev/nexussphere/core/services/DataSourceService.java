package com.biswasakashdev.nexussphere.core.services;

import com.biswasakashdev.nexussphere.core.dtos.requests.DatasourceRequest;
import reactor.core.publisher.Mono;

public interface DataSourceService {
    Mono<Void> saveDatasource(DatasourceRequest request) ;
}
