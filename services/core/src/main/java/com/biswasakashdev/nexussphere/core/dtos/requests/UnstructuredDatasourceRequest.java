package com.biswasakashdev.nexussphere.core.dtos.requests;

import com.biswasakashdev.nexussphere.core.models.DataType;

public record UnstructuredDatasourceRequest(
        DataSourceBaseDetails baseDetails
) implements DatasourceRequest {
    @Override
    public DataType getType() {
        return DataType.UNSTRUCTURED;
    }

    @Override
    public DataSourceBaseDetails getBaseDetails() {
        return baseDetails;
    }
}
