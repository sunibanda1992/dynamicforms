package com.dynamicforms.api.service;

import com.dynamicforms.api.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FormValidationService {

    private final FormConfigService formConfigService;

    public FormValidationService(FormConfigService formConfigService) {
        this.formConfigService = formConfigService;
    }

    public ValidationResponse validateFormSubmission(FormSubmission submission) {
        List<ValidationError> errors = new ArrayList<>();

        // Get the form configuration
        Map<String, FormConfig> allForms = formConfigService.getAllForms();
        FormConfig formConfig = allForms.get(submission.getFormId());

        if (formConfig == null) {
            return ValidationResponse.builder()
                    .valid(false)
                    .errors(List.of(ValidationError.builder()
                            .field("formId")
                            .message("Form configuration not found")
                            .validationType("system")
                            .build()))
                    .message("Form validation failed")
                    .build();
        }

        Map<String, Object> data = submission.getData();

        // Validate individual field validations (basic validation)
        for (FormField field : formConfig.getFields()) {
            errors.addAll(validateField(field, data));
        }

        // Validate cross-field validations
        if (formConfig.getCrossFieldValidations() != null) {
            for (CrossFieldValidation crossValidation : formConfig.getCrossFieldValidations()) {
                ValidationError error = validateCrossField(crossValidation, data);
                if (error != null) {
                    errors.add(error);
                }
            }
        }

        boolean isValid = errors.isEmpty();
        return ValidationResponse.builder()
                .valid(isValid)
                .errors(errors)
                .message(isValid ? "Form is valid" : "Form validation failed")
                .build();
    }

    private List<ValidationError> validateField(FormField field, Map<String, Object> data) {
        List<ValidationError> errors = new ArrayList<>();
        Object value = data.get(field.getName());

        if (field.getValidations() == null) {
            return errors;
        }

        for (ValidationRule rule : field.getValidations()) {
            ValidationError error = applyValidationRule(field.getName(), value, rule);
            if (error != null) {
                errors.add(error);
            }
        }

        return errors;
    }

    private ValidationError applyValidationRule(String fieldName, Object value, ValidationRule rule) {
        switch (rule.getName()) {
            case "required":
                if (Boolean.TRUE.equals(rule.getValue()) && (value == null || value.toString().trim().isEmpty())) {
                    return createFieldError(fieldName, rule.getErrorMessage());
                }
                break;

            case "requiredTrue":
                if (Boolean.TRUE.equals(rule.getValue()) && !Boolean.TRUE.equals(value)) {
                    return createFieldError(fieldName, rule.getErrorMessage());
                }
                break;

            case "minLength":
                if (value != null && value.toString().length() < (Integer) rule.getValue()) {
                    return createFieldError(fieldName, rule.getErrorMessage());
                }
                break;

            case "maxLength":
                if (value != null && value.toString().length() > (Integer) rule.getValue()) {
                    return createFieldError(fieldName, rule.getErrorMessage());
                }
                break;

            case "min":
                if (value != null) {
                    try {
                        double numValue = Double.parseDouble(value.toString());
                        double minValue = ((Number) rule.getValue()).doubleValue();
                        if (numValue < minValue) {
                            return createFieldError(fieldName, rule.getErrorMessage());
                        }
                    } catch (NumberFormatException e) {
                        return createFieldError(fieldName, "Invalid number format");
                    }
                }
                break;

            case "max":
                if (value != null) {
                    try {
                        double numValue = Double.parseDouble(value.toString());
                        double maxValue = ((Number) rule.getValue()).doubleValue();
                        if (numValue > maxValue) {
                            return createFieldError(fieldName, rule.getErrorMessage());
                        }
                    } catch (NumberFormatException e) {
                        return createFieldError(fieldName, "Invalid number format");
                    }
                }
                break;

            case "email":
                if (value != null && Boolean.TRUE.equals(rule.getValue())) {
                    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
                    if (!value.toString().matches(emailRegex)) {
                        return createFieldError(fieldName, rule.getErrorMessage());
                    }
                }
                break;

            case "pattern":
                if (value != null && !value.toString().matches(rule.getValue().toString())) {
                    return createFieldError(fieldName, rule.getErrorMessage());
                }
                break;
        }

        return null;
    }

    private ValidationError validateCrossField(CrossFieldValidation validation, Map<String, Object> data) {
        List<String> fields = validation.getFields();

        if (fields == null || fields.size() < 2) {
            return null;
        }

        switch (validation.getValidationType()) {
            case "fieldMatch":
                return validateFieldMatch(validation, data);

            case "dateRange":
                return validateDateRange(validation, data);

            case "numericComparison":
                return validateNumericComparison(validation, data);

            case "conditionalRequired":
                return validateConditionalRequired(validation, data);

            default:
                return null;
        }
    }

    private ValidationError validateFieldMatch(CrossFieldValidation validation, Map<String, Object> data) {
        List<String> fields = validation.getFields();
        Object value1 = data.get(fields.get(0));
        Object value2 = data.get(fields.get(1));

        if (value1 == null || value2 == null || !value1.toString().equals(value2.toString())) {
            return createCrossFieldError(
                    validation.getErrorField() != null ? validation.getErrorField() : fields.get(1),
                    validation.getErrorMessage()
            );
        }

        return null;
    }

    private ValidationError validateDateRange(CrossFieldValidation validation, Map<String, Object> data) {
        List<String> fields = validation.getFields();
        Object startDateObj = data.get(fields.get(0));
        Object endDateObj = data.get(fields.get(1));

        if (startDateObj == null || endDateObj == null) {
            return null; // Skip if either date is missing (handled by required validation)
        }

        try {
            LocalDate startDate = LocalDate.parse(startDateObj.toString());
            LocalDate endDate = LocalDate.parse(endDateObj.toString());

            if ("lessThan".equals(validation.getOperator())) {
                if (!startDate.isBefore(endDate)) {
                    return createCrossFieldError(
                            validation.getErrorField() != null ? validation.getErrorField() : fields.get(1),
                            validation.getErrorMessage()
                    );
                }
            }
        } catch (Exception e) {
            return createCrossFieldError(
                    validation.getErrorField() != null ? validation.getErrorField() : fields.get(1),
                    "Invalid date format"
            );
        }

        return null;
    }

    private ValidationError validateNumericComparison(CrossFieldValidation validation, Map<String, Object> data) {
        List<String> fields = validation.getFields();
        Object value1Obj = data.get(fields.get(0));
        Object value2Obj = data.get(fields.get(1));

        if (value1Obj == null || value2Obj == null) {
            return null; // Skip if either value is missing
        }

        try {
            double value1 = Double.parseDouble(value1Obj.toString());
            double value2 = Double.parseDouble(value2Obj.toString());

            switch (validation.getOperator()) {
                case "lessThan":
                    if (value1 >= value2) {
                        return createCrossFieldError(
                                validation.getErrorField() != null ? validation.getErrorField() : fields.get(1),
                                validation.getErrorMessage()
                        );
                    }
                    break;

                case "lessThanOrEqual":
                    if (value1 > value2) {
                        return createCrossFieldError(
                                validation.getErrorField() != null ? validation.getErrorField() : fields.get(1),
                                validation.getErrorMessage()
                        );
                    }
                    break;

                case "greaterThan":
                    if (value1 <= value2) {
                        return createCrossFieldError(
                                validation.getErrorField() != null ? validation.getErrorField() : fields.get(1),
                                validation.getErrorMessage()
                        );
                    }
                    break;

                case "greaterThanOrEqual":
                    if (value1 < value2) {
                        return createCrossFieldError(
                                validation.getErrorField() != null ? validation.getErrorField() : fields.get(1),
                                validation.getErrorMessage()
                        );
                    }
                    break;
            }
        } catch (NumberFormatException e) {
            return createCrossFieldError(
                    validation.getErrorField() != null ? validation.getErrorField() : fields.get(1),
                    "Invalid number format"
            );
        }

        return null;
    }

    private ValidationError validateConditionalRequired(CrossFieldValidation validation, Map<String, Object> data) {
        List<String> fields = validation.getFields();
        Object conditionFieldValue = data.get(fields.get(0));
        Object requiredFieldValue = data.get(fields.get(1));

        // If the condition field is "custom" and the required field is empty, return error
        if ("custom".equals(conditionFieldValue) &&
            (requiredFieldValue == null || requiredFieldValue.toString().trim().isEmpty())) {
            return createCrossFieldError(
                    validation.getErrorField() != null ? validation.getErrorField() : fields.get(1),
                    validation.getErrorMessage()
            );
        }

        return null;
    }

    private ValidationError createFieldError(String field, String message) {
        return ValidationError.builder()
                .field(field)
                .message(message)
                .validationType("field")
                .build();
    }

    private ValidationError createCrossFieldError(String field, String message) {
        return ValidationError.builder()
                .field(field)
                .message(message)
                .validationType("cross-field")
                .build();
    }
}
