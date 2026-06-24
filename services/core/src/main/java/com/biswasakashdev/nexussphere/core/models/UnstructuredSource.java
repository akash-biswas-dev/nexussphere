package com.biswasakashdev.nexussphere.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


// Logs, Image, Video, Audio, Text
@Table(name = "unstructured_sources")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UnstructuredSource extends DataSource{
    @Id
    private String dataSourceId;
    private String fileId;
}
