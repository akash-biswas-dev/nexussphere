package com.biswasakashdev.nexussphere.core.dtos.requests;

import com.biswasakashdev.nexussphere.core.models.DataType;

public sealed interface DatasourceRequest permits
        RepositoryDatasourceRequest,
        UnstructuredDatasourceRequest,
        TabularDatasourceRequest
{

    DataType getType();

    DataSourceBaseDetails getBaseDetails();
}
