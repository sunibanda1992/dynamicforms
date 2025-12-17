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

### Get Conditional Form
```
GET /api/forms/conditional
```
Returns a form demonstrating conditional field visibility based on field dependencies.

### Get Cross-Field Validation Form
```
GET /api/forms/cross-validation
```
Returns a form demonstrating cross-field validations (password matching, date ranges, etc.).

### Get Form by ID
```
GET /api/forms/{formId}
```
Returns a specific form configuration by ID (e.g., "registration", "contact", "conditional", or "cross-validation").

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

## Conditional Fields

Fields can have conditional visibility based on the values of other fields. This is useful for creating dynamic forms where certain fields appear or disappear based on user selections.

### Condition Structure

Each field can have a `conditions` array and a `hidden` boolean property:

```json
{
  "name": "companyName",
  "label": "Company Name",
  "controlType": "input",
  "inputType": "text",
  "hidden": true,
  "conditions": [
    {
      "dependsOn": "employmentStatus",
      "operator": "in",
      "values": ["employed", "self-employed"],
      "action": "show"
    }
  ]
}
```

### Supported Operators

- **equals**: Field value must equal a specific value
  ```json
  {
    "dependsOn": "hasExperience",
    "operator": "equals",
    "value": true,
    "action": "show"
  }
  ```

- **in**: Field value must be in a list of values
  ```json
  {
    "dependsOn": "contactMethod",
    "operator": "in",
    "values": ["phone", "sms"],
    "action": "show"
  }
  ```

### Use Cases

1. **Select-driven visibility**: Show company name field only when employment status is "employed" or "self-employed"
2. **Checkbox-driven visibility**: Show years of experience field only when "Has Experience" checkbox is checked
3. **Radio-driven visibility**: Show phone number field only when contact method is "phone" or "sms"

### Example Response

```json
{
  "name": "phoneNumber",
  "label": "Phone Number",
  "controlType": "input",
  "inputType": "tel",
  "hidden": true,
  "conditions": [
    {
      "dependsOn": "contactMethod",
      "operator": "in",
      "values": ["phone", "sms"],
      "action": "show"
    }
  ],
  "validations": [
    {
      "name": "required",
      "value": true,
      "errorMessage": "Phone number is required"
    }
  ]
}
```

To see a complete example, call `GET /api/forms/conditional`.

## Cross-Field Validations

Cross-field validations allow you to validate relationships between multiple fields in a form. This is essential for scenarios like password confirmation, date range validation, budget ranges, and conditionally required fields.

### Structure

Forms can include a `crossFieldValidations` array at the form level:

```json
{
  "formId": "cross-field-validation-form",
  "fields": [...],
  "crossFieldValidations": [
    {
      "validationType": "fieldMatch",
      "fields": ["password", "confirmPassword"],
      "operator": "equals",
      "errorMessage": "Passwords do not match",
      "errorField": "confirmPassword"
    }
  ]
}
```

### Validation Types

#### 1. Field Match (fieldMatch)
Validates that two fields have identical values (e.g., password confirmation).

```json
{
  "validationType": "fieldMatch",
  "fields": ["password", "confirmPassword"],
  "operator": "equals",
  "errorMessage": "Passwords do not match",
  "errorField": "confirmPassword"
}
```

#### 2. Date Range (dateRange)
Validates that dates are in the correct order.

```json
{
  "validationType": "dateRange",
  "fields": ["startDate", "endDate"],
  "operator": "lessThan",
  "errorMessage": "End date must be after start date",
  "errorField": "endDate"
}
```

#### 3. Numeric Comparison (numericComparison)
Validates numeric relationships between fields.

Supported operators: `lessThan`, `lessThanOrEqual`, `greaterThan`, `greaterThanOrEqual`

```json
{
  "validationType": "numericComparison",
  "fields": ["minBudget", "maxBudget"],
  "operator": "lessThanOrEqual",
  "errorMessage": "Maximum budget must be greater than or equal to minimum budget",
  "errorField": "maxBudget"
}
```

#### 4. Conditional Required (conditionalRequired)
Makes a field required based on another field's value.

```json
{
  "validationType": "conditionalRequired",
  "fields": ["agreementType", "customAgreementDetails"],
  "operator": "requiredIf",
  "errorMessage": "Custom agreement details are required when agreement type is 'Custom'",
  "errorField": "customAgreementDetails"
}
```

### Validation Endpoint

To validate a form submission with cross-field validations:

```
POST /api/validate
Content-Type: application/json
```

**Request Body:**
```json
{
  "formId": "cross-validation",
  "data": {
    "password": "MyPassword123",
    "confirmPassword": "MyPassword123",
    "startDate": "2024-01-01",
    "endDate": "2024-12-31",
    "minBudget": 1000,
    "maxBudget": 5000,
    "agreementType": "custom",
    "customAgreementDetails": "Special terms apply"
  }
}
```

**Response (Success):**
```json
{
  "valid": true,
  "errors": [],
  "message": "Form is valid"
}
```

**Response (With Errors):**
```json
{
  "valid": false,
  "errors": [
    {
      "field": "confirmPassword",
      "message": "Passwords do not match",
      "validationType": "cross-field"
    },
    {
      "field": "endDate",
      "message": "End date must be after start date",
      "validationType": "cross-field"
    }
  ],
  "message": "Form validation failed"
}
```

### Use Cases

1. **Password Confirmation**: Ensure password and confirm password fields match
2. **Date Ranges**: Validate start date is before end date
3. **Numeric Ranges**: Ensure min/max values are in correct order
4. **Conditional Requirements**: Make fields required based on other field values

To see a complete example, call `GET /api/forms/cross-validation`.

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
  crossFieldValidations?: CrossFieldValidation[];
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
  conditions?: FieldCondition[];
  hidden?: boolean;
}

export interface FieldCondition {
  dependsOn: string;
  operator: string;
  value?: any;
  values?: any[];
  action: string;
}

export interface ValidationRule {
  name: string;
  value: any;
  errorMessage: string;
}

export interface CrossFieldValidation {
  validationType: string;
  fields: string[];
  operator: string;
  errorMessage: string;
  errorField?: string;
}

export interface FormSubmission {
  formId: string;
  data: Record<string, any>;
}

export interface ValidationError {
  field: string;
  message: string;
  validationType: string;
}

export interface ValidationResponse {
  valid: boolean;
  errors: ValidationError[];
  message: string;
}

@Injectable({ providedIn: 'root' })
export class FormConfigService {
  private apiUrl = 'http://localhost:8080/api/forms';
  private validateUrl = 'http://localhost:8080/api/validate';

  constructor(private http: HttpClient) {}

  getFormConfig(formId: string): Observable<FormConfig> {
    return this.http.get<FormConfig>(`${this.apiUrl}/${formId}`);
  }

  validateForm(submission: FormSubmission): Observable<ValidationResponse> {
    return this.http.post<ValidationResponse>(this.validateUrl, submission);
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
