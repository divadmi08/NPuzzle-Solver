# Sliding Puzzle Solver - Backend Structure (Spring Boot)

## Root (puzzle-solver/)

```
puzzle-solver/
|-- pom.xml
|-- mvnw
|-- mvnw.cmd
|-- src/
|   |-- main/
|       |-- java/
|       |   |-- com/mistri/puzzle_solver/
|       |       |-- PuzzleSolverApplication.java
|       |       |-- api/
|       |       |   |-- controller/PuzzleController.java
|       |       |   |-- service/PuzzleService.java
|       |       |   |-- request/GenerateRequest.java
|       |       |   |-- request/SolveRequest.java
|       |       |   |-- response/PuzzleResponse.java
|       |       |   |-- response/PuzzleSolutionResponse.java
|       |       |-- core/
|       |       |   |-- algorithms/
|       |       |   |   |-- Solver.java
|       |       |   |   |-- Heuristic.java
|       |       |   |   |-- solvers/AStarSolver.java
|       |       |   |   |-- solvers/IDAStarSolver.java
|       |       |   |-- model/
|       |       |       |-- PuzzleState.java
|       |       |       |-- Move.java
|       |       |       |-- Node.java
|       |       |-- generator/PuzzleGenerator.java
|       |       |-- PDB/
|       |           |-- runtime/
|       |           |   |-- PDBLoader.java
|       |           |   |-- MappedPatternDatabase.java
|       |           |   |-- PatternIndexer.java
|       |           |   |-- PatternEstimator.java
|       |           |-- generation/
|       |               |-- SegmentedPatternDatabaseGenerator.java
|       |               |-- MappedDistanceTable.java
|       |               |-- LongFileQueue.java
|       |-- resources/
|           |-- application.properties
|-- pdb/
|   |-- pdb-6-6-3-a.0.bin
|   |-- pdb-6-6-3-b.0.bin
|   |-- pdb-6-6-3-c.0.bin
|   |-- pdb-6-6-3-*-work/   (temporanei di generazione)
```

## Obiettivo del backend

- Esporre API semplici per generare e risolvere puzzle 3x3 e 4x4.
- Garantire che i puzzle generati siano risolvibili.
- Risolvere 3x3 con A* e 4x4 con IDA* usando una euristica solida (Manhattan + conflitto lineare + Pattern Database).
- Restituire una sequenza di mosse leggibile dal front-end.

## Flusso principale (API -> logica -> risposta)

1. `POST /puzzle/generate`
1. `PuzzleController` valida la richiesta e passa a `PuzzleService`.
1. `PuzzleService` sceglie il numero di mosse (50 per 3x3, 200 per 4x4) e chiama `PuzzleGenerator`.
1. `PuzzleGenerator` parte dallo stato obiettivo e applica mosse casuali valide (evita mosse inverse consecutive) per garantire la solvibilita'.
1. Ritorno: `PuzzleResponse` con la griglia generata.

1. `POST /puzzle/solve`
1. `PuzzleController` passa la griglia a `PuzzleService`.
1. `PuzzleService` valida: griglia non nulla, dimensione 3 o 4, stato risolvibile.
1. Se 3x3 usa `AStarSolver`, se 4x4 usa `IDAStarSolver`.
1. I solver usano `Heuristic` per valutare gli stati.
1. Ritorno: `PuzzleSolutionResponse` con le mosse (SOPRA, SOTTO, SINISTRA, DESTRA).

## Core model e algoritmi

- `PuzzleState` e' lo stato del puzzle (array lineare di tessere, dimensione, posizione dello zero). Include:
- validazione di solvibilita' (parita' inversioni e riga dello zero per griglie pari).
- `applicaMossa` per generare stati successivi.
- codifica compatta a 4 bit per tessera per deduplicare stati in IDA*.

- `Move` definisce le mosse possibili e il delta riga/colonna.
- `Node` e' il nodo di A* con costi `g`, `h`, `f` e link al padre.

- `AStarSolver` usa una priority queue e un set chiuso con miglior `g` per trovare la soluzione minima nel 3x3.
- `IDAStarSolver` usa deepening su `f = g + h` con pruning di mosse inverse e deduplica con codifica dello stato.

## Heuristic (logica completa)

`Heuristic.stima` calcola:

- Distanza di Manhattan.
- Conflitto lineare (aggiunge 2 per ogni conflitto in riga/colonna).
- PDB (Pattern Database) solo per 4x4.

Il valore finale e':

```
max(Manhattan + conflitto lineare, PDB)
```

Questo garantisce un bound ammissibile ma piu' stretto possibile. Per 3x3, la componente PDB e' 0.

## PDB (Pattern Database) - Obiettivo

La Pattern Database e' una tabella precomputata che memorizza la distanza minima (in mosse) per portare un sottoinsieme di tessere nelle posizioni obiettivo, ignorando il resto. Serve a:

- Ridurre drasticamente il numero di nodi esplorati da IDA* sul 4x4.
- Migliorare l'euristica rispetto a Manhattan/conflitti lineari.
- Rendere la ricerca piu' stabile su istanze difficili.

In questo progetto si usa una PDB additiva con partizione 6-6-3:

- Pattern A: tessere 1..6
- Pattern B: tessere 7..12
- Pattern C: tessere 13..15

La stima PDB e' la somma delle tre tabelle, quindi rimane ammissibile.

## PDB - Runtime (caricamento e stima)

Percorso: `puzzle-solver/src/main/java/com/mistri/puzzle_solver/PDB/runtime`.

- `PDBLoader` carica i file dal percorso configurato in `application.properties`:
- `puzzle.pdb.dir=${PUZZLE_PDB_DIR:pdb}` (default `puzzle-solver/pdb`).
- Cerca prima file singoli: `pdb-6-6-3-a.bin`, `pdb-6-6-3-b.bin`, `pdb-6-6-3-c.bin`.
- Se non li trova, usa segmenti: `pdb-6-6-3-a.0.bin`, `pdb-6-6-3-b.0.bin`, `pdb-6-6-3-c.0.bin`.

- `MappedPatternDatabase` usa memory-mapped IO per leggere rapidamente i valori dalla tabella.
- `PatternIndexer` calcola il rango (indice) di uno stato di pattern con una codifica combinatoria delle posizioni di tessere + zero.
- La stima totale e' la somma delle tre tabelle: `A + B + C`.

## PDB - Generazione (build dei file)

Percorso: `puzzle-solver/src/main/java/com/mistri/puzzle_solver/PDB/generation`.

La classe principale e' `SegmentedPatternDatabaseGenerator` e genera i `.bin` tramite una BFS a costo 0/1:

- Si parte dallo stato obiettivo del pattern.
- Ogni mossa del vuoto e' valida se resta nella griglia.
- Se il vuoto scambia con una tessera del pattern, il costo dell'arco e' 1.
- Se il vuoto scambia con una tessera NON nel pattern, il costo e' 0.
- Si usa una coda per i costi 0 (stesso livello) e una coda per i costi 1 (livello successivo).
- Le distanze sono salvate in `MappedDistanceTable` (file segmentati grandi, tipicamente 1024 MB).

## PDB - Passi per rigenerarlo (obiettivo + percorso)

Obiettivo: produrre i file `.bin` necessari per la PDB 6-6-3 e metterli in `puzzle-solver/pdb`.

Passi concreti:

1. Compila il progetto (serve per eseguire le classi Java di generazione).
2. Lancia il generatore tre volte, una per ciascun pattern.
3. Verifica che i file `.bin` risultino in `puzzle-solver/pdb`.

Esempio di comandi (da `puzzle-solver/`):

```
# 1) Compila
mvn -q -DskipTests package

# 2) Genera i tre pattern (segmenti da 1024 MB)
java -cp target/classes com.mistri.puzzle_solver.PDB.generation.SegmentedPatternDatabaseGenerator pdb "1,2,3,4,5,6" 1024
java -cp target/classes com.mistri.puzzle_solver.PDB.generation.SegmentedPatternDatabaseGenerator pdb "7,8,9,10,11,12" 1024
java -cp target/classes com.mistri.puzzle_solver.PDB.generation.SegmentedPatternDatabaseGenerator pdb "13,14,15" 1024
```

Nota:

- I file temporanei `pdb-6-6-3-*-work/` possono essere eliminati dopo la generazione.
- Se vuoi cambiare la posizione dei PDB, imposta `PUZZLE_PDB_DIR` o modifica `puzzle.pdb.dir`.

## Punti chiave della logica (riassunto tecnico)

- 3x3: A* + `max(Manhattan + conflitto lineare, 0)`
- 4x4: IDA* + `max(Manhattan + conflitto lineare, PDB)`
- PDB 6-6-3 additiva, caricata via memory-mapped file, indicizzata con ranking combinatorio.
- `PuzzleService` e' il punto centrale che decide generazione o risoluzione, valida input e seleziona il solver.
