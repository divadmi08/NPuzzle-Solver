# Frontend NPuzzle - Guida Tecnica (livello scuola + extra professionale)

Questa guida spiega il frontend del progetto in modo accessibile per studenti di quarta superiore informatica, ma include anche concetti che normalmente a scuola si vedono poco (o non si vedono): gestione stato avanzata, concorrenza nelle richieste HTTP, fallback robusti e progettazione UI reattiva.

## 1) Stack usato

- Next.js (App Router) + React
- TypeScript
- Redux Toolkit (store globale)
- Tailwind CSS
- Fetch API con gestione errori tipizzata

## 2) Struttura del frontend

Percorso: `front-end-puzzle/src`

- `app/`: routing pagine (`/`, `/game`, `/custom`, `/settings`)
- `features/puzzle/components/`: componenti UI del puzzle
- `features/puzzle/store/`: store Redux, action/thunk, selectors
- `features/puzzle/api/solverApi.ts`: chiamate backend
- `features/puzzle/utils/puzzle.ts`: logica pura su griglie e mosse

## 3) Idea architetturale principale

Il frontend e' separato in 3 livelli:

1. UI (componenti React)
2. Stato applicativo (Redux Toolkit)
3. Integrazione backend (API layer)

Flusso tipico:

1. L'utente clicca un bottone nella UI.
2. La UI richiama una action/thunk dello store.
3. Il thunk aggiorna lo stato e, se serve, chiama il backend.
4. I selector leggono il nuovo stato e la UI si aggiorna automaticamente.

## 4) Stato globale: cosa c'e' di importante

File centrale: `features/puzzle/store/usePuzzleStore.ts`

Lo stato include:

- Config puzzle: `gridSize`, `initialGrid`, `solutionMoves`
- Modalita': `gameMode` (`play` oppure `replay`)
- Play manuale: `manualGrid`, `playerMoves`, `minimumMoves`
- Replay: `allStates`, `allMoves`, `step`, `isPlaying`, `speed`
- UI settings: tema, palette colori, timer
- Stato errori: `error`

Concetto avanzato #1 (non banale a scuola):
lo stato e' pensato come una mini macchina a stati.

- In `play`: puoi muovere tile e accumulare mosse.
- In `replay`: scorri una sequenza di stati pre-calcolati.

Questo evita bug logici del tipo "sto giocando e facendo autoplay insieme".

## 5) Redux Toolkit in pratica

File store: `features/puzzle/store/store.ts`

Il reducer principale e' `puzzleReducer`.

Nel file `usePuzzleStore.ts` trovi:

- `createSlice` con reducer minimale (`patchState`)
- thunk action per la logica complessa (`setGridSize`, `giveUp`, `refreshMinimumMoves`, ...)

Concetto avanzato #2:
anche se sembra "mutazione diretta" (`state.x = ...`), Redux Toolkit usa Immer sotto il cofano e mantiene immutabilita' sicura.

## 6) Concorrenza: evitare risposte API vecchie

Quando chiedi le mosse minime, possono partire piu' richieste ravvicinate.
Nel codice viene usato `minimumMovesRequestId`:

1. Ogni richiesta incrementa un ID.
2. Alla risposta si controlla se l'ID e' ancora quello corrente.
3. Se no, la risposta viene ignorata.

Concetto avanzato #3:
questa tecnica previene race condition (risposte fuori ordine).

## 7) API robuste e contratto dati

File: `features/puzzle/api/solverApi.ts`

Endpoint usati:

- `POST /generate`
- `POST /solve`
- `POST /min-moves`

Punti importanti:

- Timeout con `AbortController`
- Errori custom (`ApiConnectionError`, `ApiTimeoutError`, `ApiHttpError`, `ApiPayloadError`)
- Type guard runtime (`isGrid`, `isMoves`, `isMinMoves`) per validare il payload

Concetto avanzato #4:
TypeScript controlla i tipi a compile-time, ma non puo' fidarsi dei dati esterni a runtime.
Per questo si valida sempre la risposta del backend.

## 8) Performance percepita (UX)

In `setGeneratedPuzzle` la UI mostra subito la nuova tabella senza aspettare tutta la catena di calcoli.
Le mosse minime vengono aggiornate in modo asincrono dopo.

Questo riduce la sensazione di lentezza.

Concetto avanzato #5:
ottimizzare la "percezione" e' importante quanto ottimizzare i millisecondi reali.

## 9) Play manuale vs Replay automatico

### Play manuale

- La griglia mostrata e' `manualGrid`.
- Click tile -> controllo adiacenza con lo zero -> `playMove(direction)`.
- Alla vittoria popup con:
  - tempo risoluzione
  - mosse utente
  - confronto con mosse minime backend

### Replay

- `computeAllStates` genera tutti gli stati intermedi.
- `usePlayback` avanza con timer in base alla velocita'.
- Se arrivi in fondo, autoplay si ferma.

Concetto avanzato #6:
in replay non si ricalcola la griglia ogni frame: si usa una sequenza pre-computata. Meno lavoro, meno bug.

## 10) Pagine principali

- `/` Home: hub con card navigazione e sfondo animato puzzle-style
- `/game`: esperienza principale (navbar, griglia, pannelli replay)
- `/custom`: inserimento tabella manuale con validazioni forti
- `/settings`: tema e palette accessibilita'

## 11) Accessibilita' e UX reali

- Palette alternative per daltonismo
- Stato caricamento durante chiamate lunghe (es. surrender)
- Messaggi utente chiari in caso di API down/non risolvibile

Questi dettagli trasformano un progetto "che funziona" in un progetto "usabile".

## 12) Cose che vale studiare da questo progetto

1. Separazione logica/UI/API
2. Stato globale con Redux Toolkit
3. Thunk asincroni con fallback
4. Gestione race condition con request ID
5. Validazione payload runtime
6. Stato come macchina a stati (`play` / `replay`)

## 13) Come avviare il frontend

Da `front-end-puzzle`:

```bash
pnpm i
pnpm dev
```

Build produzione:

```bash
pnpm build
pnpm start
```

## 14) Nota didattica finale

Se studi in quarta, il salto di qualita' non e' "scrivere piu' codice", ma:

- progettare i flussi prima di implementare,
- distinguere logica pura da side effect,
- trattare API e rete come inaffidabili per definizione,
- fare codice che resta chiaro anche dopo molte feature aggiunte.

Questo frontend e' un buon esempio pratico proprio per questi punti.
