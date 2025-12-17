package com.dynamicforms.api.repository;

import com.dynamicforms.api.model.FormSchema;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class SchemaRepository {

    private final Map<String, FormSchema> schemas = new ConcurrentHashMap<>();

    public FormSchema save(FormSchema schema) {
        schemas.put(schema.getSchemaId(), schema);
        return schema;
    }

    public Optional<FormSchema> findById(String schemaId) {
        return Optional.ofNullable(schemas.get(schemaId));
    }

    public List<FormSchema> findAll() {
        return new ArrayList<>(schemas.values());
    }

    public List<FormSchema> findByStatus(String status) {
        return schemas.values().stream()
                .filter(schema -> status.equals(schema.getStatus()))
                .collect(Collectors.toList());
    }

    public List<FormSchema> findByTag(String tag) {
        return schemas.values().stream()
                .filter(schema -> schema.getTags() != null && schema.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    public List<FormSchema> findBySchemaName(String schemaName) {
        return schemas.values().stream()
                .filter(schema -> schemaName.equals(schema.getSchemaName()))
                .collect(Collectors.toList());
    }

    public void deleteById(String schemaId) {
        schemas.remove(schemaId);
    }

    public boolean existsById(String schemaId) {
        return schemas.containsKey(schemaId);
    }

    public long count() {
        return schemas.size();
    }
}
