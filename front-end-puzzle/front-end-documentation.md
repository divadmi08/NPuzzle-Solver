# Sliding Puzzle Solver - Frontend Structure (Next.js + React)

## Root (front-end-puzzle/)

```
front-end-puzzle/
|-- package.json
|-- next.config.ts
|-- public/
|-- src/
|   |-- app/
|   |   |-- layout.tsx
|   |   |-- page.tsx              (Home: Game Hub)
|   |   |-- game/
|   |   |   |-- layout.tsx
|   |   |   |-- page.tsx          (GameClient)
|   |   |-- custom/
|   |   |   |-- page.tsx          (Tabella manuale)
|   |   |-- settings/
|   |       |-- page.tsx          (Impostazioni)
|   |   |-- globals.css
|   |-- components/
|   |   |-- Provider.tsx
|   |-- features/
|   |   |-- puzzle/
|   |       |-- api/solverApi.ts
|   |       |-- components/
|   |       |   |-- GameClient.tsx
|   |       |   |-- GamePlayNavbar.tsx
|   |       |   |-- PuzzleGrid.tsx
|   |       |   |-- Controls.tsx
|   |       |   |-- MoveInfo.tsx
|   |       |   |-- SpeedControl.tsx
|   |       |   |-- ProgressBar.tsx
|   |       |   |-- MoveList.tsx
|   |       |   |-- StatesPreview.tsx
|   |       |   |-- Tile.tsx
|   |       |   |-- Header.tsx
|   |       |   |-- Footer.tsx
|   |       |-- constants/puzzle.ts
|   |       |-- hooks/usePlayback.ts
|   |       |-- store/
|   |       |   |-- usePuzzleStore.ts
|   |       |   |-- puzzleSelectors.ts
|   |       |-- types/puzzle.ts
|   |       |-- utils/puzzle.ts
|   |-- utils/cn.ts
```

## Obiettivo del frontend

- Fornire un'interfaccia completa per giocare l'8-puzzle e il 15-puzzle.
- Gestire due modalita': **play** (gioco manuale) e **replay** (riproduzione soluzione).
- Integrare il backend per:
  - generare puzzle casuali (`/puzzle/generate`);
  - risolvere un puzzle dato (`/puzzle/solve`).
- Offrire impostazioni UI: tema chiaro/scuro, palette colori (daltonismo), timer e musica.

## Flusso principale (UI -> store -> API)

1. **Home** (`/`)
   - Mostra i link a: Gioca, Tabella Manuale, Impostazioni.

1. **Gioco** (`/game`)
   - `GameClient` inizializza lo store e attiva `usePlayback` per la modalita' replay.
   - `GamePlayNavbar` gestisce le azioni principali:
     - Cambio dimensione (3x3 / 4x4) tramite `postGeneratePuzzle`.
     - Arrenditi: chiama `postSolvePuzzle` e avvia la riproduzione.
     - Toggle tema, timer, accesso rapido a impostazioni e tabella manuale.

1. **Tabella Manuale** (`/custom`)
   - Inserimento valori (0..N).
   - Validazione univocita' e range.
   - Salvataggio nello store (gioco immediato) o risoluzione via API.

1. **Impostazioni** (`/settings`)
   - Tema chiaro/scuro.
   - Palette colori per daltonismo.
   - Attivazione/disattivazione musica.

## Stato centrale (Zustand)

File: `src/features/puzzle/store/usePuzzleStore.ts`.

Responsabilita' principali:

- **Config puzzle**: `gridSize`, `initialGrid`, `solutionMoves`.
- **Stati precalcolati**: `allStates`, `allMoves`, `totalSteps`.
- **Modalita'**: `gameMode = play | replay`.
- **Replay**: `step`, `isPlaying`, `speed`, `tick()`.
- **Gioco manuale**: `playMove`, `giveUp`, `restartGame`.
- **UI settings**: tema, palette colori, musica, timer.
- **Custom board**: `setCustomBoard`, `setSolutionMovesFromApi`.

`puzzleSelectors.ts` espone selector tipizzati per l'uso nei componenti.

## Modalita' Play vs Replay

- **Play**:
  - La griglia attiva e' `manualGrid`.
  - Le mosse dell'utente usano `applyMove` e aggiornano lo stato.
  - Se risolto: overlay di vittoria.

- **Replay**:
  - `computeAllStates` genera tutti gli stati della soluzione.
  - `usePlayback` avanza lo `step` a intervalli.
  - `Controls`, `MoveList`, `ProgressBar` gestiscono navigazione e velocita'.

## API (frontend -> backend)

File: `src/features/puzzle/api/solverApi.ts`.

- Base URL: `BACKEND_API_URL` (default `http://localhost:8080/puzzle`).
- `postGeneratePuzzle(size)`:
  - richiama `/generate`, riceve la griglia iniziale.
  - fallback su configurazione locale se errore.
- `postSolvePuzzle(grid)`:
  - richiama `/solve`, riceve la lista mosse.
  - error handling con classi dedicate:
    - `ApiConnectionError`, `ApiTimeoutError`, `ApiHttpError`, `ApiPayloadError`.

## Utilita' core

File: `src/features/puzzle/utils/puzzle.ts`.

- `findZero`: individua la posizione della casella vuota.
- `applyMove`: applica una mossa e restituisce nuova griglia.
- `computeAllStates`: genera tutti gli stati intermedi.
- `isGridSolved`: verifica stato finale.
- `getMovedTile`: utile per animazioni del replay.

## Palette colori (accessibilita')

File: `src/features/puzzle/constants/puzzle.ts`.

- Palette standard + varianti per: protanopia, deuteranopia, tritanopia, achromatopsia.
- Selezione attiva nello store e usata da `Tile.tsx`.

## Componenti UI chiave

- `GameClient`: layout principale, gestisce overlay errori.
- `GamePlayNavbar`: barra superiore con timer, tema, board size e API actions.
- `PuzzleGrid` + `Tile`: rendering interattivo della griglia.
- `Controls` + `MoveInfo` + `SpeedControl` + `ProgressBar`: gestione replay.
- `MoveList` + `StatesPreview` + `Footer`: dettagli soluzione.

## Script principali

Da `package.json`:

```
npm run dev
npm run build
npm run start
npm run lint
```
