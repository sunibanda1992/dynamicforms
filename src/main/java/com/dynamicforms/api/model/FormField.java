package com.dynamicforms.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormField {
    private String name;
    private String label;
    private String controlType;
    private String inputType;
    private Object defaultValue;
    private String placeholder;
    private List<ValidationRule> validations;
    private List<SelectOption> options;
    private Map<String, Object> attributes;
    private Integer order;
    private String cssClass;
}
