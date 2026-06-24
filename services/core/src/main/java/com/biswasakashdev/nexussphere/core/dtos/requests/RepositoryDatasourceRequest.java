package com.biswasakashdev.nexussphere.core.dtos.requests;

import com.biswasakashdev.nexussphere.core.models.DataType;

public record RepositoryDatasourceRequest(
        DataSourceBaseDetails baseDetails,
        String username,
        String password,
        String url
) implements DatasourceRequest {
    @Override
    public DataType getType() {
        return DataType.REPOSITORY;
    }

    @Override
    public DataSourceBaseDetails getBaseDetails() {
        return baseDetails;
    }
}
