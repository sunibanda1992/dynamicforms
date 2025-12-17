package com.dynamicforms.api.controller;

import com.dynamicforms.api.model.FormSubmission;
import com.dynamicforms.api.model.ValidationResponse;
import com.dynamicforms.api.service.FormValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validate")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201", "http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:3000", "http://localhost:8081"})
public class FormValidationController {

    private final FormValidationService validationService;

    public FormValidationController(FormValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping
    public ResponseEntity<ValidationResponse> validateForm(@RequestBody FormSubmission submission) {
        ValidationResponse response = validationService.validateFormSubmission(submission);
        return ResponseEntity.ok(response);
    }
}
