package com.dynamicforms.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormSchema {
    private String schemaId;
    private String schemaName;
    private String schemaVersion;
    private String description;
    private FormConfig formConfig;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String status;
    private List<String> tags;
}
