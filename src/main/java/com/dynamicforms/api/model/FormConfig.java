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
public class FormConfig {
    private String formId;
    private String formTitle;
    private String formDescription;
    private List<FormField> fields;
    private String submitButtonText;
    private String cancelButtonText;
}
