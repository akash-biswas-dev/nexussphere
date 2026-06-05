package com.biswasakashdev.nexussphere.core.controller;


import com.biswasakashdev.nexussphere.common.response.Page;
import com.biswasakashdev.nexussphere.core.dtos.requests.RepositoryDatasourceRequest;
import com.biswasakashdev.nexussphere.core.dtos.requests.TabularDatasourceRequest;
import com.biswasakashdev.nexussphere.core.dtos.requests.UnstructuredDatasourceRequest;
import com.biswasakashdev.nexussphere.core.dtos.response.DataSourceResponse;
import com.biswasakashdev.nexussphere.core.services.DataSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/data-source")
public class DataSourceController {

    private final DataSourceService datasourceService;

    @GetMapping
    public Mono<Page<DataSourceResponse>> getDataSource() {
        return Mono.just(
                new Page<>(1, 10, 1, 10L, List.of())
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/repository")
    public Mono<Void> createSql(@RequestBody RepositoryDatasourceRequest request) {
        return datasourceService.saveDatasource(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/tabular")
    public Mono<Void> createTabular(@RequestBody TabularDatasourceRequest request) {
        return datasourceService.saveDatasource(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/unstructured")
    public Mono<Void> createUnstructured(@RequestBody UnstructuredDatasourceRequest request) {
        return datasourceService.saveDatasource(request);
    }
}
