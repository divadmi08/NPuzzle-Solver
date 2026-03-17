# Puzzle Solver Backend

Backend Spring Boot del progetto NPuzzle.

Espone API REST per:
- generare puzzle risolvibili
- risolvere puzzle
- calcolare il numero minimo di mosse

## Cosa fa il backend

- `POST /puzzle/generate`: genera una griglia 3x3 o 4x4 risolvibile.
- `POST /puzzle/solve`: restituisce la sequenza di mosse verso la soluzione.
- `POST /puzzle/min-moves`: restituisce il numero minimo di mosse.

Per il 4x4 usa una logica avanzata con euristiche e Pattern Database per migliorare le prestazioni.

## Prerequisiti

- Java 25 (come definito in `pom.xml`)
- Maven Wrapper (gia incluso nel progetto con `mvnw` / `mvnw.cmd`)

## Avvio locale

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Server di default:
- host: `0.0.0.0`
- porta: `8080`

## Build

Linux/macOS:

```bash
./mvnw -DskipTests package
```

Windows PowerShell:

```powershell
.\mvnw.cmd -DskipTests package
```

## Esempi richieste API

### Generate

`POST /puzzle/generate`

Body:

```json
{
  "size": 4
}
```

### Solve

`POST /puzzle/solve`

Body:

```json
{
  "grid": [[1,2,3],[4,5,6],[7,0,8]]
}
```

### Min moves

`POST /puzzle/min-moves`

Body:

```json
{
  "grid": [[1,2,3],[4,5,6],[7,0,8]]
}
```

## Nota integrazione frontend

Il frontend si aspetta il backend su:
- `http://localhost:8080/puzzle`

Se cambi porta o host backend, aggiorna `BACKEND_API_URL` nel frontend.

## Documentazione tecnica approfondita

- `../backend-documentation.md`