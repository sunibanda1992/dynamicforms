package com.dynamicforms.api.controller;

import com.dynamicforms.api.model.FormConfig;
import com.dynamicforms.api.service.FormConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/forms")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201", "http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:3000", "http://localhost:8081"})
public class FormConfigController {

    private final FormConfigService formConfigService;

    public FormConfigController(FormConfigService formConfigService) {
        this.formConfigService = formConfigService;
    }

    @GetMapping("/registration")
    public ResponseEntity<FormConfig> getRegistrationForm() {
        return ResponseEntity.ok(formConfigService.getRegistrationFormConfig());
    }

    @GetMapping("/contact")
    public ResponseEntity<FormConfig> getContactForm() {
        return ResponseEntity.ok(formConfigService.getContactFormConfig());
    }

    @GetMapping
    public ResponseEntity<Map<String, FormConfig>> getAllForms() {
        return ResponseEntity.ok(formConfigService.getAllForms());
    }

    @GetMapping("/{formId}")
    public ResponseEntity<FormConfig> getFormById(@PathVariable String formId) {
        Map<String, FormConfig> forms = formConfigService.getAllForms();
        FormConfig formConfig = forms.get(formId);

        if (formConfig != null) {
            return ResponseEntity.ok(formConfig);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
