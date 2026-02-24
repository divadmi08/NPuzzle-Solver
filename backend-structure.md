# Sliding Puzzle Solver --- Backend Structure (Spring Boot)

## Root

    backend/
    │
    ├── pom.xml
    ├── mvnw
    ├── mvnw.cmd
    └── src/
        └── main/
            ├── java/
            │   └── com/
            │       └── tuonome/
            │           └── slidingpuzzle/
            │
            │               ├── SlidingPuzzleApplication.java
            │
            │               ├── controller/
            │               │   └── PuzzleController.java
            │               │
            │               ├── service/
            │               │   ├── PuzzleService.java
            │               │   └── SolverService.java
            │               │
            │               ├── solver/
            │               │   ├── Solver.java
            │               │   ├── AStarSolver.java
            │               │   ├── IDAStarSolver.java
            │               │   └── Heuristic.java
            │               │
            │               ├── model/
            │               │   ├── PuzzleState.java
            │               │   ├── Move.java
            │               │   └── Node.java
            │               │
            │               ├── generator/
            │               │   └── PuzzleGenerator.java
            │               │
            │               ├── dto/
            │               │   ├── GenerateRequest.java
            │               │   ├── GenerateResponse.java
            │               │   ├── SolveRequest.java
            │               │   └── SolveResponse.java
            │               │
            │               └── exception/
            │                   └── SolverTimeoutException.java
            │
            └── resources/
                ├── application.properties
                └── static/

------------------------------------------------------------------------

# Explanation of each package

## SlidingPuzzleApplication.java

Spring Boot entry point. Starts the backend server.

------------------------------------------------------------------------

## controller/

Handles HTTP requests and responses.

**PuzzleController.java** - POST `/api/puzzle/generate` - POST
`/api/puzzle/solve` - Calls services and returns JSON.

------------------------------------------------------------------------

## service/

Contains business logic.

**PuzzleService.java** - Generates new puzzles - Uses `PuzzleGenerator`

**SolverService.java** - Chooses solver based on size - 3×3 →
AStarSolver - 4×4 → IDAStarSolver

------------------------------------------------------------------------

## solver/

Contains solving algorithms.

**Solver.java** - Interface for all solvers

**AStarSolver.java** - A\* implementation - Used for 3×3

**IDAStarSolver.java** - IDA\* implementation - Used for 4×4

**Heuristic.java** - Manhattan distance calculation

------------------------------------------------------------------------

## model/

Core data structures.

**PuzzleState.java** - Represents grid - Generates neighbors - Checks
goal

**Move.java** - Enum of moves - UP, DOWN, LEFT, RIGHT

**Node.java** - Used by A\* - Contains state, parent, cost

------------------------------------------------------------------------

## generator/

**PuzzleGenerator.java** - Creates solvable puzzles - Shuffles from
solved state

------------------------------------------------------------------------

## dto/

Objects used in API communication.

**GenerateRequest.java** - Puzzle size request

**GenerateResponse.java** - Generated grid

**SolveRequest.java** - Current grid

**SolveResponse.java** - Solution moves

------------------------------------------------------------------------

## exception/

**SolverTimeoutException.java** - Thrown if solving takes too long

------------------------------------------------------------------------

## resources/

**application.properties** - Backend configuration

**static/** - Optional static files

------------------------------------------------------------------------
