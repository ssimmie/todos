---
title: API Reference
has_children: true
nav_order: 3
---

# Todos API Reference

The Todos API is a REST API built using Spring Boot WebFlux (reactive) and follows HAL+JSON (Hypertext Application Language) standards for hypermedia-driven responses. The API supports creating and managing checklists and tasks with full HATEOAS compliance.

## Base URL

```
http://localhost:8181
```

## Content Types

- **Request Content-Type**: `application/json`
- **Response Content-Type**: `application/hal+json`

## Authentication

This API currently does not require authentication (development/demo version).

## Root Resource

### Get API Root
Retrieve the API root resource with links to all available endpoints.

**Endpoint**: `GET /`

**Response**: `200 OK`
```json
{
  "_links": {
    "self": {
      "href": "http://localhost:8181/"
    },
    "checklists": {
      "href": "http://localhost:8181/checklists"
    },
    "tasks": {
      "href": "http://localhost:8181/tasks"
    }
  }
}
```

## Checklists Resource

### Get Checklists Collection
Retrieve the checklists collection resource.

**Endpoint**: `GET /checklists`

**Response**: `200 OK`
```json
{
  "_links": {
    "self": {
      "href": "http://localhost:8181/checklists"
    }
  }
}
```

### Create a New Checklist
Create a new checklist with the specified name.

**Endpoint**: `POST /checklists`

**Request Body**:
```json
{
  "name": "My Shopping List"
}
```

**Response**: `201 Created`
```json
{
  "name": "My Shopping List",
  "_links": {
    "self": {
      "href": "http://localhost:8181/checklists/550e8400-e29b-41d4-a716-446655440000"
    }
  }
}
```

**Headers**:
- `Location: http://localhost:8181/checklists/550e8400-e29b-41d4-a716-446655440000`

**Error Responses**:
- `400 Bad Request` - Invalid request body or missing required fields

## Checklist Resource

### Get a Specific Checklist
Retrieve a specific checklist by its ID.

**Endpoint**: `GET /checklists/{checklistId}`

**Path Parameters**:
- `checklistId` (UUID) - The unique identifier of the checklist

**Response**: `200 OK`
```json
{
  "name": "My Shopping List",
  "_links": {
    "self": {
      "href": "http://localhost:8181/checklists/550e8400-e29b-41d4-a716-446655440000"
    }
  }
}
```

**Error Responses**:
- `404 Not Found` - Checklist with the specified ID does not exist
- `400 Bad Request` - Invalid UUID format

## Tasks Resource

### Get Tasks Collection
Retrieve the tasks collection resource.

**Endpoint**: `GET /tasks`

**Response**: `200 OK`
```json
{
  "_links": {
    "self": {
      "href": "http://localhost:8181/tasks"
    }
  }
}
```

### Create a New Task
Create a new task with the specified todo item.

**Endpoint**: `POST /tasks`

**Request Body**:
```json
{
  "todo": "Buy milk and eggs"
}
```

**Response**: `201 Created`
```json
{
  "todo": "Buy milk and eggs",
  "_links": {
    "self": {
      "href": "http://localhost:8181/tasks"
    }
  }
}
```

**Headers**:
- `Location: http://localhost:8181/tasks`

**Error Responses**:
- `400 Bad Request` - Invalid request body or missing required fields

## Data Models

### Checklist
Represents a checklist with a name and associated todos.

**Properties**:
- `name` (string, required) - The name/title of the checklist

**Example**:
```json
{
  "name": "Weekly Groceries"
}
```

### Task
Represents a single task or todo item.

**Properties**:
- `todo` (string, required) - The description of the task to be completed

**Example**:
```json
{
  "todo": "Complete API documentation"
}
```

## Error Handling

The API uses standard HTTP status codes and returns error information in JSON format:

**Error Response Format**:
```json
{
  "timestamp": "2023-09-13T19:22:53.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid request body",
  "path": "/checklists"
}
```

**Common Status Codes**:
- `200 OK` - Request successful
- `201 Created` - Resource created successfully
- `400 Bad Request` - Invalid request format or parameters
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

## HATEOAS and Hypermedia

This API follows RESTful principles with HATEOAS (Hypermedia as the Engine of Application State). All responses include `_links` sections that provide navigation links to related resources, making the API self-describing and discoverable.

**Link Relations**:
- `self` - Link to the current resource
- `checklists` - Link to the checklists collection
- `tasks` - Link to the tasks collection

## Example API Workflow

Here's a complete example of how to interact with the API:

1. **Discover API endpoints**:
   ```bash
   curl -H "Accept: application/hal+json" http://localhost:8181/
   ```

2. **Create a new checklist**:
   ```bash
   curl -X POST \
     -H "Content-Type: application/json" \
     -H "Accept: application/hal+json" \
     -d '{"name": "Weekend Tasks"}' \
     http://localhost:8181/checklists
   ```

3. **Get the created checklist** (using the ID from the Location header):
   ```bash
   curl -H "Accept: application/hal+json" \
     http://localhost:8181/checklists/550e8400-e29b-41d4-a716-446655440000
   ```

4. **Create a new task**:
   ```bash
   curl -X POST \
     -H "Content-Type: application/json" \
     -H "Accept: application/hal+json" \
     -d '{"todo": "Clean the house"}' \
     http://localhost:8181/tasks
   ```

## Technical Implementation

- **Framework**: Spring Boot 3.5.5 with WebFlux (Reactive Web)
- **Java Version**: 17
- **Architecture**: Hexagonal Architecture (Ports & Adapters)
- **Data Format**: JSON with HAL+JSON hypermedia extensions
- **Reactive**: Built on Project Reactor for non-blocking I/O

For more technical details, see the [Architecture Documentation](../index.md).