# Word Finder

Word Finder is a Spring Boot REST API that identifies dictionary words constructible from a supplied set of letters and ranks the results by letter score.

The search checks each dictionary entry against a letter-frequency table instead of generating permutations. This keeps lookup work proportional to the dictionary size and avoids factorial growth as the input gets longer.

## Features

- Finds all dictionary words that can be built from the supplied letters
- Prevents a letter from being reused unless it appears multiple times in the input
- Scores matches using configurable per-letter values
- Sorts results by descending score and then alphabetically
- Accepts uppercase or lowercase input
- Publishes an OpenAPI specification and interactive Swagger UI
- Includes a non-root, multi-stage Docker image

## Technology

- Java 17
- Spring Boot 4.1
- Maven Wrapper
- springdoc-openapi 3.0
- JUnit 5
- Docker

## Requirements

For local development:

- Java 17 or newer
- No system Maven installation is required; the Maven Wrapper is included

For containerized execution:

- Docker

## Run locally

On macOS or Linux:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

The service starts on `http://localhost:8080` by default. Override the port with the `SERVER_PORT` environment variable.

## API

### Find matching words

```http
POST /api/v1/wordfinder/letters
Content-Type: application/json
```

Request body:

```json
{
  "letters": "triangle"
}
```

Example request:

```bash
curl --request POST \
  --url http://localhost:8080/api/v1/wordfinder/letters \
  --header "Content-Type: application/json" \
  --data '{"letters":"triangle"}'
```

Example response:

```json
[
  {
    "key": "triangle",
    "value": 9
  },
  {
    "key": "altering",
    "value": 9
  }
]
```

The endpoint returns HTTP `202 Accepted`. Each result contains the dictionary word in `key` and its calculated score in `value`.

## OpenAPI documentation

With the application running:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- OpenAPI YAML: `http://localhost:8080/v3/api-docs.yaml`

The specification is generated from the controller and schema annotations, so it stays aligned with the running API.

## Configuration

Application configuration is stored in `src/main/resources/application.yaml`.

Letter scores can be changed under `word.letter-map`:

```yaml
word:
  letter-map:
    a: 1
    b: 4
    c: 4
```

The dictionary is loaded from `src/main/resources/enable1.txt` when the application starts.

## Tests

Run the complete test suite:

```powershell
.\mvnw.cmd test
```

On macOS or Linux, use `./mvnw test`.

## Docker

Build the image:

```bash
docker build -t wordfinder .
```

Run the container:

```bash
docker run --rm --name wordfinder -p 8080:8080 wordfinder
```

To use another application port:

```bash
docker run --rm --name wordfinder -e SERVER_PORT=9090 -p 9090:9090 wordfinder
```

## Project structure

```text
src/main/java/com/divvision33/wordfinder
|-- config       Application and OpenAPI configuration
|-- controller   REST endpoints
|-- model        Request models
`-- services     Dictionary loading, word matching, and scoring
```
