package com.dynamicforms.api.service;

import com.dynamicforms.api.model.*;
import com.dynamicforms.api.repository.SchemaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SchemaService {

    private final SchemaRepository schemaRepository;
    private final FormConfigService formConfigService;

    public SchemaService(SchemaRepository schemaRepository, FormConfigService formConfigService) {
        this.schemaRepository = schemaRepository;
        this.formConfigService = formConfigService;
    }

    public FormSchema createSchema(SchemaCreateRequest request) {
        String schemaId = UUID.randomUUID().toString();

        FormSchema schema = FormSchema.builder()
                .schemaId(schemaId)
                .schemaName(request.getSchemaName())
                .schemaVersion(request.getSchemaVersion() != null ? request.getSchemaVersion() : "1.0")
                .description(request.getDescription())
                .formConfig(request.getFormConfig())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(request.getCreatedBy() != null ? request.getCreatedBy() : "system")
                .status("active")
                .tags(request.getTags())
                .build();

        return schemaRepository.save(schema);
    }

    public Optional<FormSchema> getSchemaById(String schemaId) {
        return schemaRepository.findById(schemaId);
    }

    public List<FormSchema> getAllSchemas() {
        return schemaRepository.findAll();
    }

    public List<SchemaMetadata> getAllSchemaMetadata() {
        return schemaRepository.findAll().stream()
                .map(this::toMetadata)
                .collect(Collectors.toList());
    }

    public List<FormSchema> getSchemasByStatus(String status) {
        return schemaRepository.findByStatus(status);
    }

    public List<FormSchema> getSchemasByTag(String tag) {
        return schemaRepository.findByTag(tag);
    }

    public List<FormSchema> getSchemasByName(String schemaName) {
        return schemaRepository.findBySchemaName(schemaName);
    }

    public Optional<FormSchema> updateSchema(String schemaId, SchemaCreateRequest request) {
        Optional<FormSchema> existingSchema = schemaRepository.findById(schemaId);

        if (existingSchema.isPresent()) {
            FormSchema schema = existingSchema.get();
            schema.setSchemaName(request.getSchemaName());
            schema.setSchemaVersion(request.getSchemaVersion());
            schema.setDescription(request.getDescription());
            schema.setFormConfig(request.getFormConfig());
            schema.setUpdatedAt(LocalDateTime.now());
            schema.setTags(request.getTags());

            return Optional.of(schemaRepository.save(schema));
        }

        return Optional.empty();
    }

    public Optional<FormSchema> updateSchemaStatus(String schemaId, String status) {
        Optional<FormSchema> existingSchema = schemaRepository.findById(schemaId);

        if (existingSchema.isPresent()) {
            FormSchema schema = existingSchema.get();
            schema.setStatus(status);
            schema.setUpdatedAt(LocalDateTime.now());

            return Optional.of(schemaRepository.save(schema));
        }

        return Optional.empty();
    }

    public boolean deleteSchema(String schemaId) {
        if (schemaRepository.existsById(schemaId)) {
            schemaRepository.deleteById(schemaId);
            return true;
        }
        return false;
    }

    public void initializeDefaultSchemas() {
        FormConfig registrationForm = formConfigService.getRegistrationFormConfig();
        SchemaCreateRequest registrationRequest = SchemaCreateRequest.builder()
                .schemaName("user-registration")
                .schemaVersion("1.0")
                .description("User registration form schema")
                .formConfig(registrationForm)
                .createdBy("system")
                .tags(List.of("registration", "user", "onboarding"))
                .build();
        createSchema(registrationRequest);

        FormConfig contactForm = formConfigService.getContactFormConfig();
        SchemaCreateRequest contactRequest = SchemaCreateRequest.builder()
                .schemaName("contact-form")
                .schemaVersion("1.0")
                .description("Contact us form schema")
                .formConfig(contactForm)
                .createdBy("system")
                .tags(List.of("contact", "support", "inquiry"))
                .build();
        createSchema(contactRequest);
    }

    private SchemaMetadata toMetadata(FormSchema schema) {
        return SchemaMetadata.builder()
                .schemaId(schema.getSchemaId())
                .schemaName(schema.getSchemaName())
                .schemaVersion(schema.getSchemaVersion())
                .description(schema.getDescription())
                .status(schema.getStatus())
                .tags(schema.getTags())
                .createdAt(schema.getCreatedAt())
                .updatedAt(schema.getUpdatedAt())
                .createdBy(schema.getCreatedBy())
                .build();
    }
}
