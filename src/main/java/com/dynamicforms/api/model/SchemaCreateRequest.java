package com.dynamicforms.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchemaCreateRequest {
    private String schemaName;
    private String schemaVersion;
    private String description;
    private FormConfig formConfig;
    private String createdBy;
    private List<String> tags;
}
