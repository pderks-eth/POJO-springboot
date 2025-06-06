# Java Project

## Overview
This is a Java project running in a development container. This README provides information on how to set up, develop, and run the project.

## Development Environment

This project is set up to run in a development container with the following features:
- Debian GNU/Linux 11 (bullseye)
- Git (latest version built from source)
- Node.js development tools (node, npm, eslint)

## Getting Started

### Prerequisites
- Docker
- Visual Studio Code with Remote - Containers extension

### Setup
1. Clone this repository
2. Open the folder in VS Code
3. When prompted, select "Reopen in Container" or run the "Remote-Containers: Reopen in Container" command

## Running the Project
### Build the Project
Run the following command in the terminal:
```bash
./mvnw clean package
```
### Run the Project
Run the following command in the terminal:
```bash
./mvnw spring-boot:run
```
### Access the Application
Open your web browser and navigate to:
```
http://localhost:8080
```

#### Enpoints

GET alle Tasks:

```bash
curl http://localhost:8080/api/tasks
```
GET einzelner Task:

```bash
curl http://localhost:8080/api/tasks/2
```
POST neuen Task:

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"description":"Neue Aufgabe", "completed":false}'
```
PUT (komplett updaten):

```bash
curl -X PUT http://localhost:8080/api/tasks/3 \
  -H "Content-Type: application/json" \
  -d '{"description":"Aufgabe 3 aktualisiert","completed":true}'
```
PATCH (teilweise updaten):

```bash
curl -X PATCH http://localhost:8080/api/tasks/3 \
  -H "Content-Type: application/json" \
  -d '{"completed":false}'
```
DELETE einzelner Task:

```bash
curl -X DELETE http://localhost:8080/api/tasks/3
```
DELETE alle Tasks:

```bash
curl -X DELETE http://localhost:8080/api/tasks
```
