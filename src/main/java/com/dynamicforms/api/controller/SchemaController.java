package com.dynamicforms.api.controller;

import com.dynamicforms.api.model.FormSchema;
import com.dynamicforms.api.model.SchemaCreateRequest;
import com.dynamicforms.api.model.SchemaMetadata;
import com.dynamicforms.api.service.SchemaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schemas")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201", "http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:3000", "http://localhost:8081"})
public class SchemaController {

    private final SchemaService schemaService;

    public SchemaController(SchemaService schemaService) {
        this.schemaService = schemaService;
    }

    @PostMapping
    public ResponseEntity<FormSchema> createSchema(@RequestBody SchemaCreateRequest request) {
        FormSchema schema = schemaService.createSchema(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(schema);
    }

    @GetMapping("/{schemaId}")
    public ResponseEntity<FormSchema> getSchemaById(@PathVariable String schemaId) {
        return schemaService.getSchemaById(schemaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<FormSchema>> getAllSchemas(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String name) {

        List<FormSchema> schemas;

        if (status != null) {
            schemas = schemaService.getSchemasByStatus(status);
        } else if (tag != null) {
            schemas = schemaService.getSchemasByTag(tag);
        } else if (name != null) {
            schemas = schemaService.getSchemasByName(name);
        } else {
            schemas = schemaService.getAllSchemas();
        }

        return ResponseEntity.ok(schemas);
    }

    @GetMapping("/metadata")
    public ResponseEntity<List<SchemaMetadata>> getAllSchemaMetadata() {
        List<SchemaMetadata> metadata = schemaService.getAllSchemaMetadata();
        return ResponseEntity.ok(metadata);
    }

    @PutMapping("/{schemaId}")
    public ResponseEntity<FormSchema> updateSchema(
            @PathVariable String schemaId,
            @RequestBody SchemaCreateRequest request) {

        return schemaService.updateSchema(schemaId, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{schemaId}/status")
    public ResponseEntity<FormSchema> updateSchemaStatus(
            @PathVariable String schemaId,
            @RequestBody Map<String, String> statusUpdate) {

        String status = statusUpdate.get("status");
        if (status == null) {
            return ResponseEntity.badRequest().build();
        }

        return schemaService.updateSchemaStatus(schemaId, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{schemaId}")
    public ResponseEntity<Void> deleteSchema(@PathVariable String schemaId) {
        boolean deleted = schemaService.deleteSchema(schemaId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{schemaId}/form-config")
    public ResponseEntity<Object> getFormConfigBySchemaId(@PathVariable String schemaId) {
        return schemaService.getSchemaById(schemaId)
                .map(schema -> ResponseEntity.ok((Object) schema.getFormConfig()))
                .orElse(ResponseEntity.notFound().build());
    }
}
