package com.dynamicforms.api.service;

import com.dynamicforms.api.model.FormConfig;
import com.dynamicforms.api.model.FormField;
import com.dynamicforms.api.model.SelectOption;
import com.dynamicforms.api.model.ValidationRule;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    public Map<String, FormConfig> getAllForms() {
        Map<String, FormConfig> forms = new HashMap<>();
        forms.put("registration", getRegistrationFormConfig());
        forms.put("contact", getContactFormConfig());
        return forms;
    }
}
