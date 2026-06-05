package com.biswasakashdev.nexussphere.core.dtos.requests;

import com.biswasakashdev.nexussphere.core.models.DataType;

public record TabularDatasourceRequest(
        DataSourceBaseDetails baseDetails
)implements DatasourceRequest {
    @Override
    public DataType getType() {
        return DataType.TABULAR;
    }

    @Override
    public DataSourceBaseDetails getBaseDetails() {
        return baseDetails;
    }
}
