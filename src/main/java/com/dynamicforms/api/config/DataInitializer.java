package com.dynamicforms.api.config;

import com.dynamicforms.api.service.SchemaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final SchemaService schemaService;

    public DataInitializer(SchemaService schemaService) {
        this.schemaService = schemaService;
    }

    @Override
    public void run(String... args) {
        schemaService.initializeDefaultSchemas();
        System.out.println("Default form schemas initialized successfully!");
    }
}
