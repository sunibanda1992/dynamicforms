# Dynamic Forms API

A Spring Boot REST API that provides JSON configuration for dynamically rendering HTML forms in Angular applications.

## Prerequisites

- JDK 21
- Maven 3.6+ (or use the included Maven Wrapper)

## Project Structure

```
dynamicforms/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── dynamicforms/
│       │           └── api/
│       │               ├── DynamicFormsApiApplication.java
│       │               ├── config/
│       │               │   └── WebConfig.java
│       │               ├── controller/
│       │               │   └── FormConfigController.java
│       │               ├── model/
│       │               │   ├── FormConfig.java
│       │               │   ├── FormField.java
│       │               │   ├── SelectOption.java
│       │               │   └── ValidationRule.java
│       │               └── service/
│       │                   └── FormConfigService.java
│       └── resources/
│           └── application.properties
└── pom.xml
```

## Build and Run

### Option 1: Using Maven Wrapper (No Maven installation required)

**On Windows (PowerShell or Command Prompt):**
```powershell
# Build the project
.\mvnw.cmd clean install -DskipTests

# Run the application
.\mvnw.cmd spring-boot:run
```

**On Linux/Mac:**
```bash
# Build the project
./mvnw clean install -DskipTests

# Run the application
./mvnw spring-boot:run
```

### Option 2: Using Maven (if installed)

```bash
# Build the project
mvn clean install -DskipTests

# Run the application
mvn spring-boot:run
```

### Option 3: Using your IDE

1. Open the project in IntelliJ IDEA or Eclipse
2. Import as a Maven project
3. Run the `DynamicFormsApiApplication.java` main class

The application will start on `http://localhost:8080`

## API Overview

This application provides two main APIs:

1. **Form Config API** (`/api/forms`) - Simple API for pre-defined form configurations
2. **Schema Definition API** (`/api/schemas`) - Full CRUD API for managing dynamic form schemas

📘 **For detailed Schema API documentation, see [SCHEMA_API.md](SCHEMA_API.md)**

## Form Config API Endpoints

### Get All Forms
```
GET /api/forms
```
Returns a map of all available form configurations.

### Get Registration Form
```
GET /api/forms/registration
```
Returns the user registration form configuration.

### Get Contact Form
```
GET /api/forms/contact
```
Returns the contact form configuration.

### Get Form by ID
```
GET /api/forms/{formId}
```
Returns a specific form configuration by ID (e.g., "registration" or "contact").

## Schema Definition API Endpoints

The Schema API provides full CRUD operations for managing form schemas dynamically.

### Key Endpoints:

```
GET    /api/schemas/metadata          - Get all schema metadata (lightweight)
GET    /api/schemas                   - Get all schemas (with optional filters)
GET    /api/schemas/{schemaId}        - Get schema by ID
GET    /api/schemas/{schemaId}/form-config - Get only form config
POST   /api/schemas                   - Create new schema
PUT    /api/schemas/{schemaId}        - Update schema
PATCH  /api/schemas/{schemaId}/status - Update schema status
DELETE /api/schemas/{schemaId}        - Delete schema
```

### Query Parameters:
- `?status=active` - Filter by status
- `?tag=registration` - Filter by tag
- `?name=user-registration` - Filter by name

### Example: Get All Schema Metadata
```bash
curl http://localhost:8080/api/schemas/metadata
```

### Example: Create New Schema
```bash
curl -X POST http://localhost:8080/api/schemas \
  -H "Content-Type: application/json" \
  -d '{
    "schemaName": "feedback-form",
    "schemaVersion": "1.0",
    "description": "Customer feedback form",
    "createdBy": "admin",
    "tags": ["feedback"],
    "formConfig": { /* FormConfig object */ }
  }'
```

**📘 For complete Schema API documentation with examples, see [SCHEMA_API.md](SCHEMA_API.md)**

## Example API Response

```json
{
  "formId": "user-registration",
  "formTitle": "User Registration",
  "formDescription": "Please fill out the form to create your account",
  "submitButtonText": "Register",
  "cancelButtonText": "Cancel",
  "fields": [
    {
      "name": "username",
      "label": "Username",
      "controlType": "input",
      "inputType": "text",
      "placeholder": "Enter your username",
      "order": 1,
      "validations": [
        {
          "name": "required",
          "value": true,
          "errorMessage": "Username is required"
        },
        {
          "name": "minLength",
          "value": 4,
          "errorMessage": "Username must be at least 4 characters"
        },
        {
          "name": "pattern",
          "value": "^[a-zA-Z0-9_]+$",
          "errorMessage": "Username can only contain letters, numbers, and underscores"
        }
      ]
    },
    {
      "name": "email",
      "label": "Email Address",
      "controlType": "input",
      "inputType": "email",
      "placeholder": "your.email@example.com",
      "order": 2,
      "validations": [
        {
          "name": "required",
          "value": true,
          "errorMessage": "Email is required"
        },
        {
          "name": "email",
          "value": true,
          "errorMessage": "Please enter a valid email address"
        }
      ]
    }
  ]
}
```

## Form Field Types

The API supports the following control types:

- **input**: Text input fields with various input types (text, email, password, tel, number)
- **select**: Dropdown select fields with options
- **radio**: Radio button groups
- **checkbox**: Checkboxes
- **textarea**: Multi-line text areas

## Validation Rules

Each field can have multiple validation rules:

- **required**: Field is required (value: true/false)
- **requiredTrue**: Must be true (for checkboxes)
- **email**: Email format validation
- **minLength**: Minimum length (value: number)
- **maxLength**: Maximum length (value: number)
- **min**: Minimum value for numbers (value: number)
- **max**: Maximum value for numbers (value: number)
- **pattern**: Regex pattern validation (value: regex string)

Each validation includes an `errorMessage` that should be displayed when the validation fails.

## CORS Configuration

The API is configured to accept requests from `http://localhost:4200` (default Angular development server).

To modify allowed origins, update:
- `src/main/java/com/dynamicforms/api/config/WebConfig.java`
- `src/main/resources/application.properties`

## Example Angular Integration

In your Angular application, you can consume this API:

```typescript
// form-config.service.ts
export interface FormConfig {
  formId: string;
  formTitle: string;
  formDescription: string;
  fields: FormField[];
  submitButtonText: string;
  cancelButtonText: string;
}

export interface FormField {
  name: string;
  label: string;
  controlType: string;
  inputType?: string;
  placeholder?: string;
  defaultValue?: any;
  validations?: ValidationRule[];
  options?: SelectOption[];
  attributes?: Record<string, any>;
  order?: number;
}

export interface ValidationRule {
  name: string;
  value: any;
  errorMessage: string;
}

@Injectable({ providedIn: 'root' })
export class FormConfigService {
  private apiUrl = 'http://localhost:8080/api/forms';

  constructor(private http: HttpClient) {}

  getFormConfig(formId: string): Observable<FormConfig> {
    return this.http.get<FormConfig>(`${this.apiUrl}/${formId}`);
  }
}
```

## Technologies Used

- Java 21
- Spring Boot 3.2.1
- Spring Web
- Spring Validation
- Lombok
- Maven

## License

MIT
