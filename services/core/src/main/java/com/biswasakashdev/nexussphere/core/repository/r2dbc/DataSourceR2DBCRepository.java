package com.biswasakashdev.nexussphere.core.repository.r2dbc;

import com.biswasakashdev.nexussphere.core.models.DataSource;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DataSourceR2DBCRepository extends ReactiveCrudRepository<DataSource, String> {
}
