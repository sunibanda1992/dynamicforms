# Schema Definition API Documentation

## Overview

The Schema Definition API allows you to create, manage, and retrieve form schemas dynamically. Each schema contains metadata and a complete form configuration that can be used to render forms in your Angular application.

## Base URL

```
http://localhost:8080/api/schemas
```

## Schema Structure

### FormSchema
```json
{
  "schemaId": "unique-uuid",
  "schemaName": "user-registration",
  "schemaVersion": "1.0",
  "description": "User registration form schema",
  "formConfig": { /* FormConfig object */ },
  "createdAt": "2025-12-16T21:30:24.031",
  "updatedAt": "2025-12-16T21:30:24.031",
  "createdBy": "system",
  "status": "active",
  "tags": ["registration", "user", "onboarding"]
}
```

### SchemaMetadata (Lightweight)
```json
{
  "schemaId": "unique-uuid",
  "schemaName": "user-registration",
  "schemaVersion": "1.0",
  "description": "User registration form schema",
  "status": "active",
  "tags": ["registration", "user", "onboarding"],
  "createdAt": "2025-12-16T21:30:24.031",
  "updatedAt": "2025-12-16T21:30:24.031",
  "createdBy": "system"
}
```

## API Endpoints

### 1. Create Schema

Create a new form schema.

**Endpoint:** `POST /api/schemas`

**Request Body:**
```json
{
  "schemaName": "survey-form",
  "schemaVersion": "1.0",
  "description": "Customer satisfaction survey",
  "createdBy": "admin",
  "tags": ["survey", "feedback"],
  "formConfig": {
    "formId": "customer-survey",
    "formTitle": "Customer Satisfaction Survey",
    "formDescription": "Please share your feedback",
    "submitButtonText": "Submit Survey",
    "cancelButtonText": "Cancel",
    "fields": [
      {
        "name": "rating",
        "label": "Overall Satisfaction",
        "controlType": "input",
        "inputType": "number",
        "placeholder": "Rate from 1-10",
        "order": 1,
        "validations": [
          {
            "name": "required",
            "value": true,
            "errorMessage": "Rating is required"
          },
          {
            "name": "min",
            "value": 1,
            "errorMessage": "Minimum rating is 1"
          },
          {
            "name": "max",
            "value": 10,
            "errorMessage": "Maximum rating is 10"
          }
        ]
      }
    ]
  }
}
```

**Response:** `201 Created`
```json
{
  "schemaId": "abc123-def456",
  "schemaName": "survey-form",
  "schemaVersion": "1.0",
  "description": "Customer satisfaction survey",
  "formConfig": { /* full form config */ },
  "createdAt": "2025-12-16T21:30:24.031",
  "updatedAt": "2025-12-16T21:30:24.031",
  "createdBy": "admin",
  "status": "active",
  "tags": ["survey", "feedback"]
}
```

### 2. Get All Schemas

Retrieve all form schemas with optional filtering.

**Endpoint:** `GET /api/schemas`

**Query Parameters:**
- `status` (optional): Filter by status (e.g., "active", "inactive", "draft")
- `tag` (optional): Filter by tag
- `name` (optional): Filter by schema name

**Examples:**
```
GET /api/schemas
GET /api/schemas?status=active
GET /api/schemas?tag=registration
GET /api/schemas?name=user-registration
```

**Response:** `200 OK`
```json
[
  {
    "schemaId": "f83fd326-8c09-478f-a9f4-a6a61d498916",
    "schemaName": "user-registration",
    "schemaVersion": "1.0",
    "description": "User registration form schema",
    "formConfig": { /* full form config */ },
    "createdAt": "2025-12-16T21:30:24.031",
    "updatedAt": "2025-12-16T21:30:24.031",
    "createdBy": "system",
    "status": "active",
    "tags": ["registration", "user", "onboarding"]
  }
]
```

### 3. Get Schema Metadata

Retrieve lightweight metadata for all schemas (excludes formConfig for better performance).

**Endpoint:** `GET /api/schemas/metadata`

**Response:** `200 OK`
```json
[
  {
    "schemaId": "f83fd326-8c09-478f-a9f4-a6a61d498916",
    "schemaName": "user-registration",
    "schemaVersion": "1.0",
    "description": "User registration form schema",
    "status": "active",
    "tags": ["registration", "user", "onboarding"],
    "createdAt": "2025-12-16T21:30:24.031",
    "updatedAt": "2025-12-16T21:30:24.031",
    "createdBy": "system"
  }
]
```

### 4. Get Schema by ID

Retrieve a specific schema by its ID.

**Endpoint:** `GET /api/schemas/{schemaId}`

**Example:**
```
GET /api/schemas/f83fd326-8c09-478f-a9f4-a6a61d498916
```

**Response:** `200 OK` or `404 Not Found`
```json
{
  "schemaId": "f83fd326-8c09-478f-a9f4-a6a61d498916",
  "schemaName": "user-registration",
  "schemaVersion": "1.0",
  "description": "User registration form schema",
  "formConfig": { /* full form config */ },
  "createdAt": "2025-12-16T21:30:24.031",
  "updatedAt": "2025-12-16T21:30:24.031",
  "createdBy": "system",
  "status": "active",
  "tags": ["registration", "user", "onboarding"]
}
```

### 5. Get Form Config Only

Retrieve only the form configuration for a schema (excludes metadata).

**Endpoint:** `GET /api/schemas/{schemaId}/form-config`

**Example:**
```
GET /api/schemas/f83fd326-8c09-478f-a9f4-a6a61d498916/form-config
```

**Response:** `200 OK` or `404 Not Found`
```json
{
  "formId": "user-registration",
  "formTitle": "User Registration",
  "formDescription": "Please fill out the form to create your account",
  "submitButtonText": "Register",
  "cancelButtonText": "Cancel",
  "fields": [ /* array of form fields */ ]
}
```

### 6. Update Schema

Update an existing schema.

**Endpoint:** `PUT /api/schemas/{schemaId}`

**Request Body:**
```json
{
  "schemaName": "user-registration-v2",
  "schemaVersion": "2.0",
  "description": "Updated user registration form",
  "tags": ["registration", "user", "onboarding", "v2"],
  "formConfig": { /* updated form config */ }
}
```

**Response:** `200 OK` or `404 Not Found`

### 7. Update Schema Status

Update only the status of a schema.

**Endpoint:** `PATCH /api/schemas/{schemaId}/status`

**Request Body:**
```json
{
  "status": "inactive"
}
```

**Response:** `200 OK` or `404 Not Found`

### 8. Delete Schema

Delete a schema by ID.

**Endpoint:** `DELETE /api/schemas/{schemaId}`

**Example:**
```
DELETE /api/schemas/f83fd326-8c09-478f-a9f4-a6a61d498916
```

**Response:** `204 No Content` or `404 Not Found`

## Pre-loaded Schemas

The application initializes with two default schemas on startup:

1. **User Registration Schema**
   - Schema Name: `user-registration`
   - Tags: `registration`, `user`, `onboarding`
   - Fields: username, email, password, phone, age, gender, country, bio, terms

2. **Contact Form Schema**
   - Schema Name: `contact-form`
   - Tags: `contact`, `support`, `inquiry`
   - Fields: name, email, subject, message

## Use Cases

### 1. List All Available Forms
```bash
curl http://localhost:8080/api/schemas/metadata
```

### 2. Get Registration Form
```bash
# First, get metadata to find the schemaId
curl http://localhost:8080/api/schemas/metadata

# Then get the full schema
curl http://localhost:8080/api/schemas/{schemaId}

# Or get just the form config
curl http://localhost:8080/api/schemas/{schemaId}/form-config
```

### 3. Create Custom Form
```bash
curl -X POST http://localhost:8080/api/schemas \
  -H "Content-Type: application/json" \
  -d '{
    "schemaName": "feedback-form",
    "schemaVersion": "1.0",
    "description": "Product feedback form",
    "createdBy": "admin",
    "tags": ["feedback", "product"],
    "formConfig": {
      "formId": "product-feedback",
      "formTitle": "Product Feedback",
      "submitButtonText": "Submit",
      "fields": []
    }
  }'
```

### 4. Filter by Status
```bash
curl http://localhost:8080/api/schemas?status=active
```

### 5. Search by Tag
```bash
curl http://localhost:8080/api/schemas?tag=registration
```

### 6. Deactivate a Schema
```bash
curl -X PATCH http://localhost:8080/api/schemas/{schemaId}/status \
  -H "Content-Type: application/json" \
  -d '{"status": "inactive"}'
```

## Angular Integration Example

```typescript
// schema.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface SchemaMetadata {
  schemaId: string;
  schemaName: string;
  schemaVersion: string;
  description: string;
  status: string;
  tags: string[];
  createdAt: string;
  updatedAt: string;
  createdBy: string;
}

export interface FormSchema {
  schemaId: string;
  schemaName: string;
  schemaVersion: string;
  description: string;
  formConfig: any;
  createdAt: string;
  updatedAt: string;
  createdBy: string;
  status: string;
  tags: string[];
}

@Injectable({ providedIn: 'root' })
export class SchemaService {
  private apiUrl = 'http://localhost:8080/api/schemas';

  constructor(private http: HttpClient) {}

  // Get all schema metadata
  getAllMetadata(): Observable<SchemaMetadata[]> {
    return this.http.get<SchemaMetadata[]>(`${this.apiUrl}/metadata`);
  }

  // Get full schema by ID
  getSchemaById(schemaId: string): Observable<FormSchema> {
    return this.http.get<FormSchema>(`${this.apiUrl}/${schemaId}`);
  }

  // Get only form config
  getFormConfig(schemaId: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${schemaId}/form-config`);
  }

  // Get schemas by tag
  getSchemasByTag(tag: string): Observable<FormSchema[]> {
    return this.http.get<FormSchema[]>(`${this.apiUrl}?tag=${tag}`);
  }

  // Create new schema
  createSchema(schema: any): Observable<FormSchema> {
    return this.http.post<FormSchema>(this.apiUrl, schema);
  }

  // Update schema
  updateSchema(schemaId: string, schema: any): Observable<FormSchema> {
    return this.http.put<FormSchema>(`${this.apiUrl}/${schemaId}`, schema);
  }

  // Update schema status
  updateStatus(schemaId: string, status: string): Observable<FormSchema> {
    return this.http.patch<FormSchema>(
      `${this.apiUrl}/${schemaId}/status`,
      { status }
    );
  }

  // Delete schema
  deleteSchema(schemaId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${schemaId}`);
  }
}
```

## Status Values

Common status values:
- `active` - Schema is active and ready to use
- `inactive` - Schema is disabled
- `draft` - Schema is under development
- `deprecated` - Schema is outdated but kept for reference
- `archived` - Schema is archived

## Best Practices

1. **Use Metadata Endpoint for Lists**: When displaying a list of available forms, use `/api/schemas/metadata` for better performance.

2. **Version Your Schemas**: Use the `schemaVersion` field to track changes to your forms over time.

3. **Tag Appropriately**: Use tags to organize and categorize your schemas for easy filtering.

4. **Schema Naming**: Use clear, descriptive names for your schemas (e.g., "user-registration", "contact-form").

5. **Status Management**: Use status updates to control which schemas are visible to users.

6. **Form Config Separation**: Use `/api/schemas/{id}/form-config` when you only need the form configuration without metadata.

## Error Responses

- `404 Not Found` - Schema ID does not exist
- `400 Bad Request` - Invalid request body or missing required fields
- `201 Created` - Schema successfully created
- `200 OK` - Request successful
- `204 No Content` - Schema successfully deleted
