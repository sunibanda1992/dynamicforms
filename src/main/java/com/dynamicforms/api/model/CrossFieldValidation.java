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
public class CrossFieldValidation {
    private String validationType;  // e.g., "fieldMatch", "dateRange", "conditionalRequired", "numericComparison"
    private List<String> fields;    // Fields involved in the validation
    private String operator;        // e.g., "equals", "greaterThan", "lessThan", "greaterThanOrEqual", "lessThanOrEqual"
    private String errorMessage;
    private String errorField;      // Which field should display the error (optional)
}
