package com.dynamicforms.api.service;

import com.dynamicforms.api.model.CrossFieldValidation;
import com.dynamicforms.api.model.FieldCondition;
import com.dynamicforms.api.model.FormConfig;
import com.dynamicforms.api.model.FormField;
import com.dynamicforms.api.model.SelectOption;
import com.dynamicforms.api.model.ValidationRule;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class FormConfigService {

    public FormConfig getRegistrationFormConfig() {
        return FormConfig.builder()
                .formId("user-registration")
                .formTitle("User Registration")
                .formDescription("Please fill out the form to create your account")
                .submitButtonText("Register")
                .cancelButtonText("Cancel")
                .fields(Arrays.asList(
                        createTextField(),
                        createEmailField(),
                        createPasswordField(),
                        createPhoneField(),
                        createAgeField(),
                        createGenderField(),
                        createCountryField(),
                        createBioField(),
                        createTermsField()
                ))
                .build();
    }

    public FormConfig getContactFormConfig() {
        return FormConfig.builder()
                .formId("contact-form")
                .formTitle("Contact Us")
                .formDescription("We'd love to hear from you")
                .submitButtonText("Send Message")
                .cancelButtonText("Clear")
                .fields(Arrays.asList(
                        FormField.builder()
                                .name("name")
                                .label("Full Name")
                                .controlType("input")
                                .inputType("text")
                                .placeholder("Enter your full name")
                                .order(1)
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("Name is required")
                                                .build(),
                                        ValidationRule.builder()
                                                .name("minLength")
                                                .value(3)
                                                .errorMessage("Name must be at least 3 characters")
                                                .build()
                                ))
                                .build(),
                        FormField.builder()
                                .name("email")
                                .label("Email Address")
                                .controlType("input")
                                .inputType("email")
                                .placeholder("your.email@example.com")
                                .order(2)
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("Email is required")
                                                .build(),
                                        ValidationRule.builder()
                                                .name("email")
                                                .value(true)
                                                .errorMessage("Please enter a valid email address")
                                                .build()
                                ))
                                .build(),
                        FormField.builder()
                                .name("subject")
                                .label("Subject")
                                .controlType("select")
                                .placeholder("Select a subject")
                                .order(3)
                                .options(Arrays.asList(
                                        SelectOption.builder().label("General Inquiry").value("general").build(),
                                        SelectOption.builder().label("Technical Support").value("support").build(),
                                        SelectOption.builder().label("Billing Question").value("billing").build(),
                                        SelectOption.builder().label("Feedback").value("feedback").build()
                                ))
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("Please select a subject")
                                                .build()
                                ))
                                .build(),
                        FormField.builder()
                                .name("message")
                                .label("Message")
                                .controlType("textarea")
                                .placeholder("Type your message here...")
                                .order(4)
                                .attributes(Map.of("rows", 5))
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("Message is required")
                                                .build(),
                                        ValidationRule.builder()
                                                .name("minLength")
                                                .value(10)
                                                .errorMessage("Message must be at least 10 characters")
                                                .build(),
                                        ValidationRule.builder()
                                                .name("maxLength")
                                                .value(500)
                                                .errorMessage("Message cannot exceed 500 characters")
                                                .build()
                                ))
                                .build()
                ))
                .build();
    }

    private FormField createTextField() {
        return FormField.builder()
                .name("username")
                .label("Username")
                .controlType("input")
                .inputType("text")
                .placeholder("Enter your username")
                .order(1)
                .validations(Arrays.asList(
                        ValidationRule.builder()
                                .name("required")
                                .value(true)
                                .errorMessage("Username is required")
                                .build(),
                        ValidationRule.builder()
                                .name("minLength")
                                .value(4)
                                .errorMessage("Username must be at least 4 characters")
                                .build(),
                        ValidationRule.builder()
                                .name("maxLength")
                                .value(20)
                                .errorMessage("Username cannot exceed 20 characters")
                                .build(),
                        ValidationRule.builder()
                                .name("pattern")
                                .value("^[a-zA-Z0-9_]+$")
                                .errorMessage("Username can only contain letters, numbers, and underscores")
                                .build()
                ))
                .build();
    }

    private FormField createEmailField() {
        return FormField.builder()
                .name("email")
                .label("Email Address")
                .controlType("input")
                .inputType("email")
                .placeholder("your.email@example.com")
                .order(2)
                .validations(Arrays.asList(
                        ValidationRule.builder()
                                .name("required")
                                .value(true)
                                .errorMessage("Email is required")
                                .build(),
                        ValidationRule.builder()
                                .name("email")
                                .value(true)
                                .errorMessage("Please enter a valid email address")
                                .build()
                ))
                .build();
    }

    private FormField createPasswordField() {
        return FormField.builder()
                .name("password")
                .label("Password")
                .controlType("input")
                .inputType("password")
                .placeholder("Enter a strong password")
                .order(3)
                .validations(Arrays.asList(
                        ValidationRule.builder()
                                .name("required")
                                .value(true)
                                .errorMessage("Password is required")
                                .build(),
                        ValidationRule.builder()
                                .name("minLength")
                                .value(8)
                                .errorMessage("Password must be at least 8 characters")
                                .build(),
                        ValidationRule.builder()
                                .name("pattern")
                                .value("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$")
                                .errorMessage("Password must contain uppercase, lowercase, number, and special character")
                                .build()
                ))
                .build();
    }

    private FormField createPhoneField() {
        return FormField.builder()
                .name("phone")
                .label("Phone Number")
                .controlType("input")
                .inputType("tel")
                .placeholder("+1 (555) 123-4567")
                .order(4)
                .validations(Arrays.asList(
                        ValidationRule.builder()
                                .name("required")
                                .value(true)
                                .errorMessage("Phone number is required")
                                .build(),
                        ValidationRule.builder()
                                .name("pattern")
                                .value("^[+]?[(]?[0-9]{1,4}[)]?[-\\s\\.]?[(]?[0-9]{1,4}[)]?[-\\s\\.]?[0-9]{1,9}$")
                                .errorMessage("Please enter a valid phone number")
                                .build()
                ))
                .build();
    }

    private FormField createAgeField() {
        return FormField.builder()
                .name("age")
                .label("Age")
                .controlType("input")
                .inputType("number")
                .placeholder("Enter your age")
                .order(5)
                .validations(Arrays.asList(
                        ValidationRule.builder()
                                .name("required")
                                .value(true)
                                .errorMessage("Age is required")
                                .build(),
                        ValidationRule.builder()
                                .name("min")
                                .value(18)
                                .errorMessage("You must be at least 18 years old")
                                .build(),
                        ValidationRule.builder()
                                .name("max")
                                .value(120)
                                .errorMessage("Please enter a valid age")
                                .build()
                ))
                .build();
    }

    private FormField createGenderField() {
        return FormField.builder()
                .name("gender")
                .label("Gender")
                .controlType("radio")
                .order(6)
                .options(Arrays.asList(
                        SelectOption.builder().label("Male").value("male").build(),
                        SelectOption.builder().label("Female").value("female").build(),
                        SelectOption.builder().label("Other").value("other").build(),
                        SelectOption.builder().label("Prefer not to say").value("not_specified").build()
                ))
                .validations(Arrays.asList(
                        ValidationRule.builder()
                                .name("required")
                                .value(true)
                                .errorMessage("Please select your gender")
                                .build()
                ))
                .build();
    }

    private FormField createCountryField() {
        return FormField.builder()
                .name("country")
                .label("Country")
                .controlType("select")
                .placeholder("Select your country")
                .order(7)
                .options(Arrays.asList(
                        SelectOption.builder().label("United States").value("US").build(),
                        SelectOption.builder().label("United Kingdom").value("UK").build(),
                        SelectOption.builder().label("Canada").value("CA").build(),
                        SelectOption.builder().label("Australia").value("AU").build(),
                        SelectOption.builder().label("India").value("IN").build(),
                        SelectOption.builder().label("Germany").value("DE").build(),
                        SelectOption.builder().label("France").value("FR").build()
                ))
                .validations(Arrays.asList(
                        ValidationRule.builder()
                                .name("required")
                                .value(true)
                                .errorMessage("Please select your country")
                                .build()
                ))
                .build();
    }

    private FormField createBioField() {
        return FormField.builder()
                .name("bio")
                .label("Bio")
                .controlType("textarea")
                .placeholder("Tell us about yourself...")
                .order(8)
                .attributes(new HashMap<>(Map.of("rows", 4, "cols", 50)))
                .validations(Arrays.asList(
                        ValidationRule.builder()
                                .name("maxLength")
                                .value(500)
                                .errorMessage("Bio cannot exceed 500 characters")
                                .build()
                ))
                .build();
    }

    private FormField createTermsField() {
        return FormField.builder()
                .name("terms")
                .label("I agree to the Terms and Conditions")
                .controlType("checkbox")
                .order(9)
                .validations(Arrays.asList(
                        ValidationRule.builder()
                                .name("requiredTrue")
                                .value(true)
                                .errorMessage("You must accept the terms and conditions")
                                .build()
                ))
                .build();
    }

    public FormConfig getConditionalFormConfig() {
        return FormConfig.builder()
                .formId("conditional-form")
                .formTitle("Conditional Fields Example")
                .formDescription("Form demonstrating conditional field visibility")
                .submitButtonText("Submit")
                .cancelButtonText("Cancel")
                .fields(Arrays.asList(
                        // Employment Status field
                        FormField.builder()
                                .name("employmentStatus")
                                .label("Employment Status")
                                .controlType("select")
                                .placeholder("Select your employment status")
                                .order(1)
                                .options(Arrays.asList(
                                        SelectOption.builder().label("Employed").value("employed").build(),
                                        SelectOption.builder().label("Self-Employed").value("self-employed").build(),
                                        SelectOption.builder().label("Unemployed").value("unemployed").build(),
                                        SelectOption.builder().label("Student").value("student").build()
                                ))
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("Employment status is required")
                                                .build()
                                ))
                                .build(),

                        // Company Name - shown only if employed or self-employed
                        FormField.builder()
                                .name("companyName")
                                .label("Company Name")
                                .controlType("input")
                                .inputType("text")
                                .placeholder("Enter company name")
                                .order(2)
                                .hidden(true)
                                .conditions(Arrays.asList(
                                        FieldCondition.builder()
                                                .dependsOn("employmentStatus")
                                                .operator("in")
                                                .values(Arrays.asList("employed", "self-employed"))
                                                .action("show")
                                                .build()
                                ))
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("Company name is required")
                                                .build()
                                ))
                                .build(),

                        // University Name - shown only if student
                        FormField.builder()
                                .name("universityName")
                                .label("University Name")
                                .controlType("input")
                                .inputType("text")
                                .placeholder("Enter university name")
                                .order(3)
                                .hidden(true)
                                .conditions(Arrays.asList(
                                        FieldCondition.builder()
                                                .dependsOn("employmentStatus")
                                                .operator("equals")
                                                .value("student")
                                                .action("show")
                                                .build()
                                ))
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("University name is required")
                                                .build()
                                ))
                                .build(),

                        // Has Experience checkbox
                        FormField.builder()
                                .name("hasExperience")
                                .label("Do you have previous experience?")
                                .controlType("checkbox")
                                .order(4)
                                .build(),

                        // Years of Experience - shown only if hasExperience is checked
                        FormField.builder()
                                .name("yearsExperience")
                                .label("Years of Experience")
                                .controlType("input")
                                .inputType("number")
                                .placeholder("Enter years of experience")
                                .order(5)
                                .hidden(true)
                                .conditions(Arrays.asList(
                                        FieldCondition.builder()
                                                .dependsOn("hasExperience")
                                                .operator("equals")
                                                .value(true)
                                                .action("show")
                                                .build()
                                ))
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("Years of experience is required")
                                                .build(),
                                        ValidationRule.builder()
                                                .name("min")
                                                .value(0)
                                                .errorMessage("Years cannot be negative")
                                                .build()
                                ))
                                .build(),

                        // Preferred Contact Method
                        FormField.builder()
                                .name("contactMethod")
                                .label("Preferred Contact Method")
                                .controlType("radio")
                                .order(6)
                                .options(Arrays.asList(
                                        SelectOption.builder().label("Email").value("email").build(),
                                        SelectOption.builder().label("Phone").value("phone").build(),
                                        SelectOption.builder().label("SMS").value("sms").build()
                                ))
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("Please select a contact method")
                                                .build()
                                ))
                                .build(),

                        // Phone Number - shown only if phone or sms is selected
                        FormField.builder()
                                .name("phoneNumber")
                                .label("Phone Number")
                                .controlType("input")
                                .inputType("tel")
                                .placeholder("+1 (555) 123-4567")
                                .order(7)
                                .hidden(true)
                                .conditions(Arrays.asList(
                                        FieldCondition.builder()
                                                .dependsOn("contactMethod")
                                                .operator("in")
                                                .values(Arrays.asList("phone", "sms"))
                                                .action("show")
                                                .build()
                                ))
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("Phone number is required")
                                                .build(),
                                        ValidationRule.builder()
                                                .name("pattern")
                                                .value("^[+]?[(]?[0-9]{1,4}[)]?[-\\s\\.]?[(]?[0-9]{1,4}[)]?[-\\s\\.]?[0-9]{1,9}$")
                                                .errorMessage("Please enter a valid phone number")
                                                .build()
                                ))
                                .build(),

                        // Email - shown only if email is selected
                        FormField.builder()
                                .name("emailAddress")
                                .label("Email Address")
                                .controlType("input")
                                .inputType("email")
                                .placeholder("your.email@example.com")
                                .order(8)
                                .hidden(true)
                                .conditions(Arrays.asList(
                                        FieldCondition.builder()
                                                .dependsOn("contactMethod")
                                                .operator("equals")
                                                .value("email")
                                                .action("show")
                                                .build()
                                ))
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("Email is required")
                                                .build(),
                                        ValidationRule.builder()
                                                .name("email")
                                                .value(true)
                                                .errorMessage("Please enter a valid email address")
                                                .build()
                                ))
                                .build()
                ))
                .build();
    }

    public FormConfig getCrossFieldValidationFormConfig() {
        return FormConfig.builder()
                .formId("cross-field-validation-form")
                .formTitle("Cross-Field Validation Example")
                .formDescription("Form demonstrating cross-field validations")
                .submitButtonText("Submit")
                .cancelButtonText("Cancel")
                .fields(Arrays.asList(
                        // Password field
                        FormField.builder()
                                .name("password")
                                .label("Password")
                                .controlType("input")
                                .inputType("password")
                                .placeholder("Enter password")
                                .order(1)
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("Password is required")
                                                .build(),
                                        ValidationRule.builder()
                                                .name("minLength")
                                                .value(8)
                                                .errorMessage("Password must be at least 8 characters")
                                                .build()
                                ))
                                .build(),

                        // Confirm Password field
                        FormField.builder()
                                .name("confirmPassword")
                                .label("Confirm Password")
                                .controlType("input")
                                .inputType("password")
                                .placeholder("Re-enter password")
                                .order(2)
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("Please confirm your password")
                                                .build()
                                ))
                                .build(),

                        // Start Date field
                        FormField.builder()
                                .name("startDate")
                                .label("Start Date")
                                .controlType("input")
                                .inputType("date")
                                .order(3)
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("Start date is required")
                                                .build()
                                ))
                                .build(),

                        // End Date field
                        FormField.builder()
                                .name("endDate")
                                .label("End Date")
                                .controlType("input")
                                .inputType("date")
                                .order(4)
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("End date is required")
                                                .build()
                                ))
                                .build(),

                        // Min Budget field
                        FormField.builder()
                                .name("minBudget")
                                .label("Minimum Budget")
                                .controlType("input")
                                .inputType("number")
                                .placeholder("Enter minimum budget")
                                .order(5)
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("Minimum budget is required")
                                                .build(),
                                        ValidationRule.builder()
                                                .name("min")
                                                .value(0)
                                                .errorMessage("Budget cannot be negative")
                                                .build()
                                ))
                                .build(),

                        // Max Budget field
                        FormField.builder()
                                .name("maxBudget")
                                .label("Maximum Budget")
                                .controlType("input")
                                .inputType("number")
                                .placeholder("Enter maximum budget")
                                .order(6)
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("Maximum budget is required")
                                                .build(),
                                        ValidationRule.builder()
                                                .name("min")
                                                .value(0)
                                                .errorMessage("Budget cannot be negative")
                                                .build()
                                ))
                                .build(),

                        // Agreement Type field
                        FormField.builder()
                                .name("agreementType")
                                .label("Agreement Type")
                                .controlType("select")
                                .placeholder("Select agreement type")
                                .order(7)
                                .options(Arrays.asList(
                                        SelectOption.builder().label("Standard").value("standard").build(),
                                        SelectOption.builder().label("Custom").value("custom").build()
                                ))
                                .validations(Arrays.asList(
                                        ValidationRule.builder()
                                                .name("required")
                                                .value(true)
                                                .errorMessage("Agreement type is required")
                                                .build()
                                ))
                                .build(),

                        // Custom Agreement Details - conditionally required
                        FormField.builder()
                                .name("customAgreementDetails")
                                .label("Custom Agreement Details")
                                .controlType("textarea")
                                .placeholder("Provide custom agreement details")
                                .order(8)
                                .hidden(true)
                                .conditions(Arrays.asList(
                                        FieldCondition.builder()
                                                .dependsOn("agreementType")
                                                .operator("equals")
                                                .value("custom")
                                                .action("show")
                                                .build()
                                ))
                                .build()
                ))
                .crossFieldValidations(Arrays.asList(
                        // Password match validation
                        CrossFieldValidation.builder()
                                .validationType("fieldMatch")
                                .fields(Arrays.asList("password", "confirmPassword"))
                                .operator("equals")
                                .errorMessage("Passwords do not match")
                                .errorField("confirmPassword")
                                .build(),

                        // Date range validation
                        CrossFieldValidation.builder()
                                .validationType("dateRange")
                                .fields(Arrays.asList("startDate", "endDate"))
                                .operator("lessThan")
                                .errorMessage("End date must be after start date")
                                .errorField("endDate")
                                .build(),

                        // Numeric comparison validation
                        CrossFieldValidation.builder()
                                .validationType("numericComparison")
                                .fields(Arrays.asList("minBudget", "maxBudget"))
                                .operator("lessThanOrEqual")
                                .errorMessage("Maximum budget must be greater than or equal to minimum budget")
                                .errorField("maxBudget")
                                .build(),

                        // Conditional required validation
                        CrossFieldValidation.builder()
                                .validationType("conditionalRequired")
                                .fields(Arrays.asList("agreementType", "customAgreementDetails"))
                                .operator("requiredIf")
                                .errorMessage("Custom agreement details are required when agreement type is 'Custom'")
                                .errorField("customAgreementDetails")
                                .build()
                ))
                .build();
    }

    public Map<String, FormConfig> getAllForms() {
        Map<String, FormConfig> forms = new HashMap<>();
        forms.put("registration", getRegistrationFormConfig());
        forms.put("contact", getContactFormConfig());
        forms.put("conditional", getConditionalFormConfig());
        forms.put("cross-validation", getCrossFieldValidationFormConfig());
        return forms;
    }
}
