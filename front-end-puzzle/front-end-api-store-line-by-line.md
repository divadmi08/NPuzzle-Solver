# Frontend API + Store - Versione Copiabile

In questa versione trovi il codice reale dentro il documento, cosi puoi copiare i blocchi direttamente.

## 1) API - solverApi.ts

Codice completo:

~~~ts
import type { Direction, Grid, GridSize, PuzzleConfig } from '@/features/puzzle/types/puzzle';
const BACKEND_API_URL: string = process.env.BACKEND_API_URL || 'http://localhost:8080/puzzle';
const REQUEST_TIMEOUT_MS = 20000;

export class ApiConnectionError extends Error {
  constructor(url: string, cause?: unknown) {
    super(`Cannot reach the server at "${url}". Check that the backend is running and the address is correct.`);
    this.name = 'ApiConnectionError';
    this.cause = cause;
  }
}

export class ApiTimeoutError extends Error {
  constructor(url: string) {
    super(`Request to "${url}" timed out. The server may be overloaded or unreachable.`);
    this.name = 'ApiTimeoutError';
  }
}

export class ApiHttpError extends Error {
  constructor(public readonly status: number, endpoint: string) {
    super(`Server returned HTTP ${status} for "${endpoint}".`);
    this.name = 'ApiHttpError';
  }
}

export class ApiPayloadError extends Error {
  constructor(endpoint: string) {
    super(`Unexpected response payload from "${endpoint}". The API contract may have changed.`);
    this.name = 'ApiPayloadError';
  }
}

export function isUnsolvableApiError(error: unknown): boolean {
  if (error instanceof ApiHttpError && error.status === 500) {
    return true;
  }

  if (error instanceof Error) {
    const message = error.message.toLowerCase();
    return message.includes('not solvable') || message.includes('non risolvibile');
  }

  return false;
}

export function getApiErrorMessage(error: unknown, fallbackMessage: string): string {
  if (isUnsolvableApiError(error)) {
    return 'Puzzle non risolvibile.';
  }
  if (error instanceof ApiConnectionError) {
    return 'Impossibile raggiungere il server backend.';
  }
  if (error instanceof ApiTimeoutError) {
    return 'Il server non ha risposto in tempo.';
  }
  if (error instanceof ApiHttpError) {
    return `Errore dal server (HTTP ${error.status}).`;
  }
  if (error instanceof ApiPayloadError) {
    return 'Risposta API non valida.';
  }
  return fallbackMessage;
}

function handleFetchError(error: unknown, url: string): never {
  if (error instanceof ApiConnectionError || error instanceof ApiTimeoutError || error instanceof ApiHttpError || error instanceof ApiPayloadError) {
    throw error;
  }
  if (error instanceof DOMException && error.name === 'AbortError') {
    throw new ApiTimeoutError(url);
  }
  // TypeError from fetch means a network/address-level failure (DNS, ECONNREFUSED, etc.)
  if (error instanceof TypeError) {
    throw new ApiConnectionError(url, error);
  }
  throw new Error(`Unexpected error: ${String(error)}`);
}

function isGrid(value: unknown): value is Grid {
  return Array.isArray(value) && value.every((row) => Array.isArray(row) && row.every((cell) => typeof cell === 'number'));
}

function isMoves(value: unknown): value is Direction[] {
  return Array.isArray(value) && value.every((move) => typeof move === 'string');
}

function isMinMoves(value: unknown): value is number {
  return typeof value === 'number' && Number.isInteger(value) && value >= 0;
}

async function postJson(path: string, body: unknown): Promise<{ endpoint: string; payload: unknown }> {
  const endpoint = BACKEND_API_URL + path;
  const controller = new AbortController();
  const timeoutId = setTimeout(() => controller.abort(), REQUEST_TIMEOUT_MS);

  try {
    const response = await fetch(endpoint, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
      signal: controller.signal,
    });

    if (!response.ok) {
      throw new ApiHttpError(response.status, endpoint);
    }

    let payload: unknown;
    try {
      payload = await response.json();
    } catch {
      throw new ApiPayloadError(endpoint);
    }

    return { endpoint, payload };
  } catch (error) {
    handleFetchError(error, endpoint);
  } finally {
    clearTimeout(timeoutId);
  }
}


export async function postSolvePuzzle(currentGrid: Grid): Promise<Direction[]> {
  const { endpoint, payload } = await postJson('/solve', { grid: currentGrid });
  const moves = (payload as { moves?: unknown })?.moves;
  if (!isMoves(moves)) {
    throw new ApiPayloadError(endpoint);
  }

  return moves;
}

export async function postGeneratePuzzle(gridSize: GridSize): Promise<PuzzleConfig> {
  const { endpoint, payload } = await postJson('/generate', { size: gridSize });
  const grid = (payload as { grid?: unknown })?.grid;

  if (!isGrid(grid)) {
    throw new ApiPayloadError(endpoint);
  }

  return {
    gridSize,
    initialGrid: grid,
    moves: [],
  };
}

export async function postMinMovesPuzzle(currentGrid: Grid): Promise<number> {
  const { endpoint, payload } = await postJson('/min-moves', { grid: currentGrid });
  const minMoves = (payload as { minMoves?: unknown })?.minMoves;
  if (!isMinMoves(minMoves)) {
    throw new ApiPayloadError(endpoint);
  }

  return minMoves;
}

~~~

Spiegazione rapida:
- Le classi ApiConnectionError, ApiTimeoutError, ApiHttpError, ApiPayloadError separano i tipi di guasto rete/server/payload.
- postJson centralizza fetch + timeout + parsing JSON + normalizzazione errori.
- postSolvePuzzle, postGeneratePuzzle, postMinMovesPuzzle sono wrapper endpoint-specifici con validazione runtime del payload.

Spiegazione estesa:
- Questo file segue una regola importante: il backend non e' affidabile per definizione, quindi ogni risposta viene verificata prima di essere usata.
- Le classi di errore servono per distinguere problemi diversi a UI:
  - connessione assente,
  - timeout,
  - risposta HTTP non valida,
  - JSON con formato sbagliato.
- handleFetchError evita if sparsi nei componenti: tutti gli errori vengono tradotti una volta sola qui.
- isGrid, isMoves, isMinMoves sono controlli runtime: TypeScript da solo non basta, perche i dati arrivano da rete.
- postJson e' il cuore tecnico: costruisce endpoint, gestisce timeout, parse JSON e rilancia errori gia' classificati.
- I 3 wrapper finali (solve, generate, min-moves) rendono il resto dell'app piu semplice: i componenti chiamano funzioni ad alto livello senza preoccuparsi dei dettagli HTTP.
- Questo approccio riduce bug sottili, ad esempio usare payload incompleti senza accorgersene.

## 2) Store Root - store.ts

Codice completo:

~~~ts
import { configureStore } from '@reduxjs/toolkit';
import { puzzleReducer } from './usePuzzleStore';

export const store = configureStore({
  reducer: {
    puzzle: puzzleReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

~~~

Spiegazione rapida:
- configureStore crea lo store Redux.
- puzzleReducer viene montato nella chiave puzzle.
- RootState e AppDispatch tipizzano selector e dispatch nel resto del frontend.

Spiegazione estesa:
- Questo file e' piccolo ma fondamentale: definisce il contenitore globale dello stato.
- La chiave puzzle indica dove vive lo stato nel tree Redux: quindi nei selector si accede con s.puzzle.
- RootState evita errori di tipo nei selector e rende autocomplete affidabile.
- AppDispatch tipizza anche i thunk async, quindi dispatch(...) accetta sia action semplici sia action asincrone.
- In pratica: qui si crea il contratto tecnico tra store, selector e componenti React.

## 3) Store Principale - usePuzzleStore.ts

Codice completo:

~~~ts
import { createSlice, type PayloadAction, type ThunkAction, type UnknownAction } from '@reduxjs/toolkit';
import type { ColorPaletteMode, Direction, Grid, GridSize, ThemeMode } from '@/features/puzzle/types/puzzle';
import { DEFAULT_GRID_SIZE, DEFAULT_SPEED, PUZZLE_CONFIGS } from '@/features/puzzle/constants/puzzle';
import { computeAllStates, applyMove, isGridSolved } from '@/features/puzzle/utils/puzzle';
import { getApiErrorMessage, isUnsolvableApiError, postMinMovesPuzzle, postSolvePuzzle } from '@/features/puzzle/api/solverApi';

const defaultConfig = PUZZLE_CONFIGS[DEFAULT_GRID_SIZE];
const { grids: defaultGrids, moves: defaultMoves } = computeAllStates(
  defaultConfig.initialGrid,
  defaultConfig.moves,
);

function cloneGrid(grid: Grid): Grid {
  return grid.map(row => [...row]);
}

export interface PuzzleState {
  // Config corrente
  gridSize: GridSize;
  initialGrid: Grid;
  solutionMoves: Direction[];

  // Dati pre-calcolati
  allStates: Grid[];
  allMoves: (Direction | null)[];
  totalSteps: number;

  // Modalita gioco
  gameMode: 'play' | 'replay';
  manualGrid: Grid;
  playerMoves: number;
  minimumMoves: number | null;
  minimumMovesRequestId: number;

  // Settings globali UI
  themeMode: ThemeMode;
  colorPaletteMode: ColorPaletteMode;
  musicEnabled: boolean;

  // Stato replay/animazione
  step: number;
  isPlaying: boolean;
  speed: number;
  elapsedSeconds: number;
  timerEnabled: boolean;

  // Derivati
  currentGrid: Grid;
  prevGrid: Grid | null;
  currentMove: Direction | null;
  isSolved: boolean;
  error: string | null;
}

const initialState: PuzzleState = {
  // Config corrente
  gridSize: defaultConfig.gridSize,
  initialGrid: cloneGrid(defaultConfig.initialGrid),
  solutionMoves: [...defaultConfig.moves],

  // Dati pre-calcolati
  allStates: defaultGrids,
  allMoves: defaultMoves,
  totalSteps: defaultGrids.length - 1,

  // Modalita gioco - Inizia in 'play'
  gameMode: 'play',
  manualGrid: cloneGrid(defaultGrids[0]),
  playerMoves: 0,
  minimumMoves: null,
  minimumMovesRequestId: 0,

  // Settings globali
  themeMode: 'dark',
  colorPaletteMode: 'default',
  musicEnabled: true,

  // Stato replay/animazione iniziale
  step: 0,
  isPlaying: false,
  speed: DEFAULT_SPEED,
  elapsedSeconds: 0,
  timerEnabled: true,

  // Derivati
  currentGrid: cloneGrid(defaultGrids[0]),
  prevGrid: null,
  currentMove: null,
  isSolved: false,
  error: null,
};

const puzzleSlice = createSlice({
  name: 'puzzle',
  initialState,
  reducers: {
    patchState: (state, action: PayloadAction<Partial<PuzzleState>>) => {
      Object.assign(state, action.payload);
    },
  },
});

const { patchState } = puzzleSlice.actions;

export const puzzleReducer = puzzleSlice.reducer;

type PuzzleRootState = { puzzle: PuzzleState };
type PuzzleThunk<ReturnType = void> = ThunkAction<ReturnType, PuzzleRootState, unknown, UnknownAction>;

export const setStep = (step: number): PuzzleThunk => (dispatch, getState) => {
  const { allStates, allMoves, totalSteps, gameMode } = getState().puzzle;
  if (gameMode !== 'replay') return;

  const clamped = Math.max(0, Math.min(totalSteps, step));
  dispatch(patchState({
    step: clamped,
    currentGrid: allStates[clamped],
    prevGrid: clamped > 0 ? allStates[clamped - 1] : null,
    currentMove: allMoves[clamped],
    isSolved: clamped === totalSteps,
  }));
};

export const goNext = (): PuzzleThunk => (dispatch, getState) => {
  const { step, totalSteps, gameMode } = getState().puzzle;
  if (gameMode !== 'replay' || step >= totalSteps) return;
  dispatch(setStep(step + 1));
};

export const goPrev = (): PuzzleThunk => (dispatch, getState) => {
  const { step, gameMode } = getState().puzzle;
  if (gameMode !== 'replay' || step <= 0) return;
  dispatch(setStep(step - 1));
};

export const play = (): PuzzleThunk => (dispatch, getState) => {
  const { step, totalSteps, gameMode } = getState().puzzle;
  if (gameMode !== 'replay') return;
  if (step >= totalSteps) dispatch(setStep(0));
  dispatch(patchState({ isPlaying: true }));
};

export const pause = (): PuzzleThunk => (dispatch) => {
  dispatch(patchState({ isPlaying: false }));
};

export const togglePlay = (): PuzzleThunk => (dispatch, getState) => {
  const { isPlaying, gameMode } = getState().puzzle;
  if (gameMode !== 'replay') return;
  if (isPlaying) {
    dispatch(pause());
  } else {
    dispatch(play());
  }
};

export const setSpeed = (speed: number): PuzzleThunk => (dispatch) => {
  dispatch(patchState({ speed }));
};

export const jumpToStep = (step: number): PuzzleThunk => (dispatch, getState) => {
  const { gameMode } = getState().puzzle;
  if (gameMode !== 'replay') return;
  dispatch(pause());
  dispatch(setStep(step));
};

export const tick = (): PuzzleThunk<boolean> => (dispatch, getState) => {
  const { step, totalSteps, gameMode } = getState().puzzle;
  if (gameMode !== 'replay' || step >= totalSteps) {
    dispatch(pause());
    return false;
  }
  dispatch(setStep(step + 1));
  return true;
};

export const refreshMinimumMoves = (grid: Grid): PuzzleThunk<Promise<void>> => async (dispatch, getState) => {
  const nextRequestId = getState().puzzle.minimumMovesRequestId + 1;
  dispatch(patchState({ minimumMovesRequestId: nextRequestId, minimumMoves: null }));

  try {
    const minMoves = await postMinMovesPuzzle(grid);
    if (getState().puzzle.minimumMovesRequestId !== nextRequestId) return;
    dispatch(patchState({ minimumMoves: minMoves }));
  } catch (minMovesError) {
    if (getState().puzzle.minimumMovesRequestId !== nextRequestId) return;
    console.warn('Impossibile ottenere le mosse minime dal backend (/min-moves), provo fallback su /solve:', minMovesError);

    try {
      const solutionMoves = await postSolvePuzzle(grid);
      if (getState().puzzle.minimumMovesRequestId !== nextRequestId) return;
      dispatch(patchState({ minimumMoves: solutionMoves.length }));
    } catch (solveError) {
      if (getState().puzzle.minimumMovesRequestId !== nextRequestId) return;
      console.warn('Impossibile ottenere le mosse minime anche dal fallback /solve:', solveError);
      dispatch(patchState({ minimumMoves: null }));
    }
  }
};

export const playMove = (direction: Direction): PuzzleThunk => (dispatch, getState) => {
  const { gameMode, manualGrid, isSolved, playerMoves } = getState().puzzle;
  if (gameMode !== 'play' || isSolved) return;

  try {
    const newGrid = applyMove(manualGrid, direction);
    const solved = isGridSolved(newGrid);
    dispatch(patchState({
      manualGrid: newGrid,
      isSolved: solved,
      playerMoves: playerMoves + 1,
    }));
  } catch (error) {
    console.warn('Invalid move:', error);
  }
};

export const giveUp = (): PuzzleThunk<Promise<void>> => async (dispatch, getState) => {
  const { manualGrid } = getState().puzzle;

  try {
    // Chiama l'API per ottenere la soluzione del puzzle corrente
    const solutionMoves = await postSolvePuzzle(manualGrid);

    // Genera gli stati dal puzzle mischiato (inizio) al goal (fine)
    const { grids, moves: allMoves } = computeAllStates(manualGrid, solutionMoves);

    dispatch(patchState({
      gameMode: 'replay',
      initialGrid: cloneGrid(manualGrid),
      solutionMoves: [...solutionMoves],
      step: 0,
      currentGrid: grids[0],
      prevGrid: null,
      currentMove: allMoves[0],
      isPlaying: true,
      allStates: grids,
      allMoves,
      totalSteps: grids.length - 1,
      error: null,
    }));
  } catch (error) {
    console.error('Errore nel risolvere il puzzle:', error);

    const errorMessage = isUnsolvableApiError(error)
      ? '❌ Puzzle non risolvibile\n\nIl puzzle attuale non può essere risolto. Prova a fare altre mosse o ricarica il gioco!'
      : getApiErrorMessage(error, 'Errore nella risoluzione del puzzle. Riprova!');

    dispatch(patchState({ error: errorMessage }));
  }
};

export const restartGame = (): PuzzleThunk => (dispatch, getState) => {
  const { allStates } = getState().puzzle;
  const puzzleStart = allStates[allStates.length - 1] || allStates[0];

  dispatch(patchState({
    gameMode: 'play',
    manualGrid: cloneGrid(puzzleStart),
    step: 0,
    isPlaying: false,
    currentGrid: cloneGrid(puzzleStart),
    prevGrid: null,
    currentMove: null,
    isSolved: false,
    elapsedSeconds: 0,
    playerMoves: 0,
  }));
};

export const setGridSize = (size: GridSize): PuzzleThunk => (dispatch) => {
  const config = PUZZLE_CONFIGS[size];
  const { grids, moves } = computeAllStates(config.initialGrid, config.moves);

  dispatch(patchState({
    gridSize: size,
    initialGrid: cloneGrid(config.initialGrid),
    solutionMoves: [...config.moves],
    allStates: grids,
    allMoves: moves,
    totalSteps: grids.length - 1,
    gameMode: 'play',
    manualGrid: cloneGrid(grids[0]),
    step: 0,
    isPlaying: false,
    currentGrid: cloneGrid(grids[0]),
    prevGrid: null,
    currentMove: null,
    isSolved: false,
    elapsedSeconds: 0,
    playerMoves: 0,
    minimumMoves: null,
  }));

  void dispatch(refreshMinimumMoves(grids[0]));
};

export const tickElapsed = (): PuzzleThunk => (dispatch, getState) => {
  const { gameMode, isSolved, elapsedSeconds } = getState().puzzle;
  if (gameMode !== 'play' || isSolved) return;
  dispatch(patchState({ elapsedSeconds: elapsedSeconds + 1 }));
};

export const resetElapsed = (): PuzzleThunk => (dispatch) => {
  dispatch(patchState({ elapsedSeconds: 0 }));
};

export const setTimerEnabled = (enabled: boolean): PuzzleThunk => (dispatch) => {
  dispatch(patchState({ timerEnabled: enabled }));
};

export const toggleTimerEnabled = (): PuzzleThunk => (dispatch, getState) => {
  dispatch(patchState({ timerEnabled: !getState().puzzle.timerEnabled }));
};

export const setThemeMode = (mode: ThemeMode): PuzzleThunk => (dispatch) => {
  dispatch(patchState({ themeMode: mode }));
};

export const toggleThemeMode = (): PuzzleThunk => (dispatch, getState) => {
  const { themeMode } = getState().puzzle;
  dispatch(patchState({ themeMode: themeMode === 'dark' ? 'light' : 'dark' }));
};

export const setColorPaletteMode = (mode: ColorPaletteMode): PuzzleThunk => (dispatch) => {
  dispatch(patchState({ colorPaletteMode: mode }));
};

export const toggleMusicEnabled = (): PuzzleThunk => (dispatch, getState) => {
  dispatch(patchState({ musicEnabled: !getState().puzzle.musicEnabled }));
};

export const clearError = (): PuzzleThunk => (dispatch) => {
  dispatch(patchState({ error: null }));
};

export const setCustomBoard = (size: GridSize, grid: Grid): PuzzleThunk => (dispatch) => {
  const customGrid = cloneGrid(grid);

  dispatch(patchState({
    gridSize: size,
    initialGrid: customGrid,
    solutionMoves: [],
    allStates: [customGrid],
    allMoves: [null],
    totalSteps: 0,
    gameMode: 'play',
    manualGrid: cloneGrid(customGrid),
    playerMoves: 0,
    minimumMoves: null,
    step: 0,
    isPlaying: false,
    currentGrid: cloneGrid(customGrid),
    prevGrid: null,
    currentMove: null,
    isSolved: isGridSolved(customGrid),
    elapsedSeconds: 0,
  }));

  void dispatch(refreshMinimumMoves(customGrid));
};

export const setGeneratedPuzzle = (
  size: GridSize,
  initialGrid: Grid,
  _moves: Direction[],
): PuzzleThunk<Promise<void>> => async (dispatch) => {
  const scrambledGrid = cloneGrid(initialGrid);

  // Rendering immediato: evitiamo solve al cambio tabella per ridurre i tempi percepiti.
  dispatch(patchState({
    gridSize: size,
    initialGrid: cloneGrid(scrambledGrid),
    solutionMoves: [],
    allStates: [scrambledGrid],
    allMoves: [null],
    totalSteps: 0,
    gameMode: 'play',
    manualGrid: cloneGrid(scrambledGrid),
    playerMoves: 0,
    minimumMoves: null,
    step: 0,
    isPlaying: false,
    currentGrid: cloneGrid(scrambledGrid),
    prevGrid: null,
    currentMove: null,
    isSolved: isGridSolved(scrambledGrid),
    elapsedSeconds: 0,
    error: null,
  }));

  void dispatch(refreshMinimumMoves(scrambledGrid));
};

export const setSolutionMovesFromApi = (moves: Direction[]): PuzzleThunk => (dispatch, getState) => {
  const { initialGrid } = getState().puzzle;
  const { grids, moves: allMoves } = computeAllStates(initialGrid, moves);

  dispatch(patchState({
    solutionMoves: [...moves],
    allStates: grids,
    allMoves,
    totalSteps: grids.length - 1,
    gameMode: 'replay',
    playerMoves: 0,
    step: 0,
    isPlaying: false,
    currentGrid: grids[0],
    prevGrid: null,
    currentMove: allMoves[0],
    isSolved: false,
  }));
};

export const getGridSizeForApi = (state: PuzzleRootState): GridSize => state.puzzle.gridSize;

~~~

Spiegazione rapida:
- Contiene stato globale PuzzleState + initialState.
- Usa createSlice con reducer patchState per aggiornamenti parziali.
- Espone thunk per tutta la logica: replay, timer, mosse manuali, surrender, cambio dimensione, custom board, mosse minime.
- minimumMovesRequestId evita race condition nelle chiamate concorrenti a backend.

Spiegazione estesa:
- Questo e' il file con la logica principale del gioco, non solo un "contenitore dati".
- PuzzleState descrive sia dati persistenti (es. gridSize) sia dati di sessione (es. step, isPlaying, elapsedSeconds).
- La separazione gameMode play/replay evita stati incoerenti: molte funzioni fanno guard-clause per bloccare operazioni fuori contesto.
- patchState riduce codice ripetitivo nei dispatch, ma va usato con criterio per non sovrascrivere campi non voluti.
- Le funzioni setStep/goNext/goPrev/play/pause/tick compongono una piccola macchina a stati del replay.
- refreshMinimumMoves mostra una tecnica avanzata:
  - genera un requestId,
  - invalida risposte vecchie,
  - prova fallback su endpoint diverso.
- playMove incrementa playerMoves solo dopo una mossa valida: in questo modo il contatore rispecchia la realta di gioco.
- giveUp trasforma una partita manuale in replay completo, ricostruendo tutti gli stati intermedi da soluzione backend.
- setGridSize/setCustomBoard/setGeneratedPuzzle fanno reset coerente dello stato e rilanciano il calcolo mosse minime in background.
- In generale, questo file centralizza le regole di business: i componenti UI restano piu semplici e meno fragili.

## 4) Selectors/Actions Hook - puzzleSelectors.ts

Codice completo:

~~~ts
import { useCallback } from 'react';
import { useDispatch, useSelector, type TypedUseSelectorHook } from 'react-redux';
import type { ColorPaletteMode, Direction, Grid, GridSize, ThemeMode } from '@/features/puzzle/types/puzzle';
import type { AppDispatch, RootState } from './store';
import {
  clearError,
  giveUp,
  goNext,
  goPrev,
  jumpToStep,
  pause,
  play,
  playMove,
  refreshMinimumMoves,
  resetElapsed,
  restartGame,
  setColorPaletteMode,
  setCustomBoard,
  setGeneratedPuzzle,
  setGridSize,
  setSolutionMovesFromApi,
  setSpeed,
  setStep,
  setThemeMode,
  setTimerEnabled,
  tick,
  tickElapsed,
  toggleMusicEnabled,
  togglePlay,
  toggleThemeMode,
  toggleTimerEnabled,
} from './usePuzzleStore';

const useAppDispatch = () => useDispatch<AppDispatch>();
const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;

// State selectors
export const useGridSize = () => useAppSelector(s => s.puzzle.gridSize);
export const useInitialGrid = () => useAppSelector(s => s.puzzle.initialGrid);
export const useSolutionMoves = () => useAppSelector(s => s.puzzle.solutionMoves);
export const useAllStates = () => useAppSelector(s => s.puzzle.allStates);
export const useAllMoves = () => useAppSelector(s => s.puzzle.allMoves);
export const useTotalSteps = () => useAppSelector(s => s.puzzle.totalSteps);
export const useStep = () => useAppSelector(s => s.puzzle.step);
export const useIsPlaying = () => useAppSelector(s => s.puzzle.isPlaying);
export const useSpeed = () => useAppSelector(s => s.puzzle.speed);
export const useElapsedSeconds = () => useAppSelector(s => s.puzzle.elapsedSeconds);
export const useTimerEnabled = () => useAppSelector(s => s.puzzle.timerEnabled);
export const useThemeMode = () => useAppSelector(s => s.puzzle.themeMode);
export const useColorPaletteMode = () => useAppSelector(s => s.puzzle.colorPaletteMode);
export const useMusicEnabled = () => useAppSelector(s => s.puzzle.musicEnabled);
export const useCurrentGrid = () => useAppSelector(s => s.puzzle.currentGrid);
export const usePrevGrid = () => useAppSelector(s => s.puzzle.prevGrid);
export const useCurrentMove = () => useAppSelector(s => s.puzzle.currentMove);
export const useIsSolved = () => useAppSelector(s => s.puzzle.isSolved);
export const useError = () => useAppSelector(s => s.puzzle.error);

// Game mode selectors
export const useGameMode = () => useAppSelector(s => s.puzzle.gameMode);
export const useManualGrid = () => useAppSelector(s => s.puzzle.manualGrid);
export const usePlayerMoves = () => useAppSelector(s => s.puzzle.playerMoves);
export const useMinimumMoves = () => useAppSelector(s => s.puzzle.minimumMoves);

// Replay action selectors
export const useSetStep = () => {
  const dispatch = useAppDispatch();
  return useCallback((stepValue: number) => {
    dispatch(setStep(stepValue));
  }, [dispatch]);
};

export const useGoNext = () => {
  const dispatch = useAppDispatch();
  return useCallback(() => {
    dispatch(goNext());
  }, [dispatch]);
};

export const useGoPrev = () => {
  const dispatch = useAppDispatch();
  return useCallback(() => {
    dispatch(goPrev());
  }, [dispatch]);
};

export const usePlay = () => {
  const dispatch = useAppDispatch();
  return useCallback(() => {
    dispatch(play());
  }, [dispatch]);
};

export const usePause = () => {
  const dispatch = useAppDispatch();
  return useCallback(() => {
    dispatch(pause());
  }, [dispatch]);
};

export const useTogglePlay = () => {
  const dispatch = useAppDispatch();
  return useCallback(() => {
    dispatch(togglePlay());
  }, [dispatch]);
};

export const useSetSpeed = () => {
  const dispatch = useAppDispatch();
  return useCallback((speedValue: number) => {
    dispatch(setSpeed(speedValue));
  }, [dispatch]);
};

export const useJumpToStep = () => {
  const dispatch = useAppDispatch();
  return useCallback((stepValue: number) => {
    dispatch(jumpToStep(stepValue));
  }, [dispatch]);
};

export const useTick = () => {
  const dispatch = useAppDispatch();
  return useCallback((): boolean => dispatch(tick()) as boolean, [dispatch]);
};

// Game mode action selectors
export const useRefreshMinimumMoves = () => {
  const dispatch = useAppDispatch();
  return useCallback((grid: Grid) => dispatch(refreshMinimumMoves(grid)) as Promise<void>, [dispatch]);
};

export const usePlayMove = () => {
  const dispatch = useAppDispatch();
  return useCallback((direction: Direction) => {
    dispatch(playMove(direction));
  }, [dispatch]);
};

export const useGiveUp = () => {
  const dispatch = useAppDispatch();
  return useCallback(() => dispatch(giveUp()) as Promise<void>, [dispatch]);
};

export const useRestartGame = () => {
  const dispatch = useAppDispatch();
  return useCallback(() => {
    dispatch(restartGame());
  }, [dispatch]);
};

export const useSetGridSize = () => {
  const dispatch = useAppDispatch();
  return useCallback((size: GridSize) => {
    dispatch(setGridSize(size));
  }, [dispatch]);
};

export const useTickElapsed = () => {
  const dispatch = useAppDispatch();
  return useCallback(() => {
    dispatch(tickElapsed());
  }, [dispatch]);
};

export const useResetElapsed = () => {
  const dispatch = useAppDispatch();
  return useCallback(() => {
    dispatch(resetElapsed());
  }, [dispatch]);
};

export const useSetTimerEnabled = () => {
  const dispatch = useAppDispatch();
  return useCallback((enabled: boolean) => {
    dispatch(setTimerEnabled(enabled));
  }, [dispatch]);
};

export const useToggleTimerEnabled = () => {
  const dispatch = useAppDispatch();
  return useCallback(() => {
    dispatch(toggleTimerEnabled());
  }, [dispatch]);
};

export const useSetThemeMode = () => {
  const dispatch = useAppDispatch();
  return useCallback((mode: ThemeMode) => {
    dispatch(setThemeMode(mode));
  }, [dispatch]);
};

export const useToggleThemeMode = () => {
  const dispatch = useAppDispatch();
  return useCallback(() => {
    dispatch(toggleThemeMode());
  }, [dispatch]);
};

export const useSetColorPaletteMode = () => {
  const dispatch = useAppDispatch();
  return useCallback((mode: ColorPaletteMode) => {
    dispatch(setColorPaletteMode(mode));
  }, [dispatch]);
};

export const useToggleMusicEnabled = () => {
  const dispatch = useAppDispatch();
  return useCallback(() => {
    dispatch(toggleMusicEnabled());
  }, [dispatch]);
};

export const useClearError = () => {
  const dispatch = useAppDispatch();
  return useCallback(() => {
    dispatch(clearError());
  }, [dispatch]);
};

export const useSetCustomBoard = () => {
  const dispatch = useAppDispatch();
  return useCallback((size: GridSize, grid: Grid) => {
    dispatch(setCustomBoard(size, grid));
  }, [dispatch]);
};

export const useSetGeneratedPuzzle = () => {
  const dispatch = useAppDispatch();
  return useCallback((size: GridSize, initialGrid: Grid, moves: Direction[]) => (
    dispatch(setGeneratedPuzzle(size, initialGrid, moves)) as Promise<void>
  ), [dispatch]);
};

export const useSetSolutionMovesFromApi = () => {
  const dispatch = useAppDispatch();
  return useCallback((moves: Direction[]) => {
    dispatch(setSolutionMovesFromApi(moves));
  }, [dispatch]);
};

~~~

Spiegazione rapida:
- Espone hook di lettura stato (useGridSize, useGameMode, ecc).
- Espone hook azioni (useSetGridSize, usePlayMove, useGiveUp, ecc).
- Ogni hook wrappa dispatch(...) in useCallback per stabilita referenziale nei componenti React.

Spiegazione estesa:
- Questo file e' il ponte tra React e Redux: i componenti importano hook pronti e non conoscono i dettagli interni dello store.
- useAppSelector e useAppDispatch sono versioni tipizzate che evitano cast manuali in ogni componente.
- I selector di lettura mantengono i componenti dichiarativi: ogni componente legge solo i campi che usa.
- Gli hook azione incapsulano dispatch e migliorano la leggibilita del codice UI.
- useCallback aiuta a mantenere riferimenti stabili quando le funzioni passano come props a componenti figli.
- Beneficio pratico: se in futuro cambi implementazione interna di un thunk, la firma dei hook puo restare uguale e i componenti non si rompono.

## 5) Flussi pratici end-to-end (azione utente -> API -> store -> UI)

### Flusso A - Click su Arrenditi

1. L'utente preme il bottone Arrenditi in GamePlayNavbar.
2. Il componente chiama useGiveUp(), che dispatcha giveUp().
3. giveUp() prende manualGrid dallo stato e chiama postSolvePuzzle(manualGrid).
4. solverApi.ts fa POST su /solve, valida payload.moves con isMoves.
5. Se valido, usePuzzleStore.ts esegue computeAllStates(manualGrid, solutionMoves).
6. Lo store passa a gameMode: replay, aggiorna allStates/allMoves/totalSteps e mette isPlaying: true.
7. usePlayback (hook) inizia a chiamare tick() a intervalli.
8. PuzzleGrid legge currentGrid e mostra l'animazione del replay.

Cosa impari:
- un'azione UI scatena un thunk async,
- il thunk coordina backend + trasformazione dati,
- lo stato aggiornato guida automaticamente il rendering.

### Flusso B - Cambio tabella (3x3 o 4x4)

1. L'utente seleziona la dimensione in GamePlayNavbar.
2. Viene chiamata postGeneratePuzzle(size).
3. solverApi.ts fa POST su /generate e valida payload.grid con isGrid.
4. GamePlayNavbar chiama setGeneratedPuzzle(size, grid, moves).
5. Lo store aggiorna subito la griglia (rendering immediato, UX veloce).
6. Poi lancia refreshMinimumMoves(scrambledGrid) in background.
7. refreshMinimumMoves prova /min-moves, e se serve fallback su /solve.
8. Header e navbar mostrano minimumMoves appena il dato arriva.

Cosa impari:
- separazione tra rendering veloce e dati secondari,
- fallback controllato quando endpoint principale fallisce.

### Flusso C - Mossa manuale sul puzzle

1. L'utente clicca una tessera vicina allo zero in PuzzleGrid.
2. PuzzleGrid calcola la direzione e chiama playMove(direction).
3. Lo store controlla guard-clause: deve essere gameMode play e non risolto.
4. applyMove(manualGrid, direction) produce newGrid.
5. isGridSolved(newGrid) aggiorna stato vittoria.
6. playerMoves incrementa solo se la mossa e' valida.
7. UI aggiorna contatore mosse e griglia senza altro codice nel componente.

Cosa impari:
- regole di business centralizzate nello store,
- componenti UI piu semplici (presentazione + input utente).

### Flusso D - Mosse minime con protezione race condition

1. Parte refreshMinimumMoves(grid).
2. Lo store incrementa minimumMovesRequestId e resetta minimumMoves.
3. Fa richiesta async al backend.
4. Quando arriva la risposta, controlla che requestId sia ancora quello corrente.
5. Se nel frattempo e' partita una richiesta nuova, la risposta vecchia viene ignorata.
6. Solo la risposta piu recente puo aggiornare minimumMoves.

Cosa impari:
- tecnica robusta per richieste concorrenti,
- evita bug visivi dove appare un valore vecchio dopo un cambio tabella rapido.

### Mini checklist di debug (utile per verifiche veloci)

- Se Arrenditi non parte: controlla errore in giveUp() e tipo eccezione in solverApi.ts.
- Se non vedi mosse minime: verifica endpoint /min-moves e fallback /solve.
- Se replay non avanza: controlla isPlaying, speed e ritorno di tick().
- Se contatori strani: verifica playerMoves e guard-clause di playMove().

## 6) Store a pezzetti: funzione per funzione (quasi riga per riga)

File di riferimento: src/features/puzzle/store/usePuzzleStore.ts

### 6.1 Blocco slice base (createSlice + patchState)

~~~ts
const puzzleSlice = createSlice({
  name: 'puzzle',
  initialState,
  reducers: {
    patchState: (state, action: PayloadAction<Partial<PuzzleState>>) => {
      Object.assign(state, action.payload);
    },
  },
});

const { patchState } = puzzleSlice.actions;
export const puzzleReducer = puzzleSlice.reducer;
~~~

Spiegazione quasi riga per riga:
- createSlice crea reducer + action creator in un colpo solo.
- name e' il namespace Redux della slice.
- initialState e' lo stato di partenza del puzzle.
- patchState riceve un oggetto parziale e lo fonde nello stato.
- Object.assign qui funziona bene con Redux Toolkit (Immer gestisce la mutabilita apparente).
- patchState viene estratta dalle actions per usarla nei thunk.
- puzzleReducer e' esportato per essere montato nello store globale.

### 6.2 setStep(step)

~~~ts
export const setStep = (step: number): PuzzleThunk => (dispatch, getState) => {
  const { allStates, allMoves, totalSteps, gameMode } = getState().puzzle;
  if (gameMode !== 'replay') return;

  const clamped = Math.max(0, Math.min(totalSteps, step));
  dispatch(patchState({
    step: clamped,
    currentGrid: allStates[clamped],
    prevGrid: clamped > 0 ? allStates[clamped - 1] : null,
    currentMove: allMoves[clamped],
    isSolved: clamped === totalSteps,
  }));
};
~~~

Spiegazione:
- legge lo stato corrente dal ramo puzzle.
- guard-clause: fuori replay non fa nulla.
- clamped evita step negativi o oltre la fine.
- aggiorna step e griglie visualizzate (corrente e precedente).
- currentMove serve a evidenziare la mossa in UI.
- isSolved diventa true solo all'ultimo step.

### 6.3 tick()

~~~ts
export const tick = (): PuzzleThunk<boolean> => (dispatch, getState) => {
  const { step, totalSteps, gameMode } = getState().puzzle;
  if (gameMode !== 'replay' || step >= totalSteps) {
    dispatch(pause());
    return false;
  }
  dispatch(setStep(step + 1));
  return true;
};
~~~

Spiegazione:
- tick e' il "frame" dell'autoplay.
- se replay non attivo o terminato, mette in pausa.
- ritorna false per dire al chiamante che non ci sono altri passi.
- se puo avanzare, usa setStep(step + 1).
- ritorna true: il replay puo continuare.

### 6.4 refreshMinimumMoves(grid)

~~~ts
export const refreshMinimumMoves = (grid: Grid): PuzzleThunk<Promise<void>> => async (dispatch, getState) => {
  const nextRequestId = getState().puzzle.minimumMovesRequestId + 1;
  dispatch(patchState({ minimumMovesRequestId: nextRequestId, minimumMoves: null }));

  try {
    const minMoves = await postMinMovesPuzzle(grid);
    if (getState().puzzle.minimumMovesRequestId !== nextRequestId) return;
    dispatch(patchState({ minimumMoves: minMoves }));
  } catch (minMovesError) {
    if (getState().puzzle.minimumMovesRequestId !== nextRequestId) return;
    console.warn('Impossibile ottenere le mosse minime dal backend (/min-moves), provo fallback su /solve:', minMovesError);

    try {
      const solutionMoves = await postSolvePuzzle(grid);
      if (getState().puzzle.minimumMovesRequestId !== nextRequestId) return;
      dispatch(patchState({ minimumMoves: solutionMoves.length }));
    } catch (solveError) {
      if (getState().puzzle.minimumMovesRequestId !== nextRequestId) return;
      console.warn('Impossibile ottenere le mosse minime anche dal fallback /solve:', solveError);
      dispatch(patchState({ minimumMoves: null }));
    }
  }
};
~~~

Spiegazione:
- genera un requestId progressivo per invalidare risposte vecchie.
- resetta subito minimumMoves a null (UI puo mostrare loading).
- prova endpoint dedicato /min-moves.
- prima di aggiornare stato, ricontrolla requestId (anti-race condition).
- se /min-moves fallisce, fallback su /solve.
- dal fallback ricava il minimo tramite solutionMoves.length.
- se fallisce anche fallback, lascia minimumMoves a null.

### 6.5 playMove(direction)

~~~ts
export const playMove = (direction: Direction): PuzzleThunk => (dispatch, getState) => {
  const { gameMode, manualGrid, isSolved, playerMoves } = getState().puzzle;
  if (gameMode !== 'play' || isSolved) return;

  try {
    const newGrid = applyMove(manualGrid, direction);
    const solved = isGridSolved(newGrid);
    dispatch(patchState({
      manualGrid: newGrid,
      isSolved: solved,
      playerMoves: playerMoves + 1,
    }));
  } catch (error) {
    console.warn('Invalid move:', error);
  }
};
~~~

Spiegazione:
- funziona solo in modalita play.
- se il puzzle e' gia risolto, ignora input.
- applyMove prova lo spostamento della tessera.
- isGridSolved calcola vittoria dopo la mossa.
- incrementa playerMoves solo se la mossa e' valida.
- catch evita crash su mosse illegali.

### 6.6 giveUp()

~~~ts
export const giveUp = (): PuzzleThunk<Promise<void>> => async (dispatch, getState) => {
  const { manualGrid } = getState().puzzle;

  try {
    const solutionMoves = await postSolvePuzzle(manualGrid);
    const { grids, moves: allMoves } = computeAllStates(manualGrid, solutionMoves);

    dispatch(patchState({
      gameMode: 'replay',
      initialGrid: cloneGrid(manualGrid),
      solutionMoves: [...solutionMoves],
      step: 0,
      currentGrid: grids[0],
      prevGrid: null,
      currentMove: allMoves[0],
      isPlaying: true,
      allStates: grids,
      allMoves,
      totalSteps: grids.length - 1,
      error: null,
    }));
  } catch (error) {
    console.error('Errore nel risolvere il puzzle:', error);

    const errorMessage = isUnsolvableApiError(error)
      ? '❌ Puzzle non risolvibile\n\nIl puzzle attuale non può essere risolto. Prova a fare altre mosse o ricarica il gioco!'
      : getApiErrorMessage(error, 'Errore nella risoluzione del puzzle. Riprova!');

    dispatch(patchState({ error: errorMessage }));
  }
};
~~~

Spiegazione:
- legge la griglia attuale giocata manualmente.
- chiede la soluzione al backend.
- ricostruisce tutti gli stati intermedi per il replay.
- aggiorna stato per partire da replay step 0.
- isPlaying true avvia subito autoplay.
- in errore, crea messaggio utente adatto al tipo di eccezione.

### 6.7 setGridSize(size)

~~~ts
export const setGridSize = (size: GridSize): PuzzleThunk => (dispatch) => {
  const config = PUZZLE_CONFIGS[size];
  const { grids, moves } = computeAllStates(config.initialGrid, config.moves);

  dispatch(patchState({
    gridSize: size,
    initialGrid: cloneGrid(config.initialGrid),
    solutionMoves: [...config.moves],
    allStates: grids,
    allMoves: moves,
    totalSteps: grids.length - 1,
    gameMode: 'play',
    manualGrid: cloneGrid(grids[0]),
    step: 0,
    isPlaying: false,
    currentGrid: cloneGrid(grids[0]),
    prevGrid: null,
    currentMove: null,
    isSolved: false,
    elapsedSeconds: 0,
    playerMoves: 0,
    minimumMoves: null,
  }));

  void dispatch(refreshMinimumMoves(grids[0]));
};
~~~

Spiegazione:
- seleziona preset 3x3 o 4x4.
- calcola stati base dalla config scelta.
- resetta tutta la sessione di gioco coerentemente.
- disattiva replay e resetta contatori.
- in coda rilancia fetch mosse minime per la nuova griglia.

### 6.8 setGeneratedPuzzle(size, initialGrid, _moves)

~~~ts
export const setGeneratedPuzzle = (
  size: GridSize,
  initialGrid: Grid,
  _moves: Direction[],
): PuzzleThunk<Promise<void>> => async (dispatch) => {
  const scrambledGrid = cloneGrid(initialGrid);

  dispatch(patchState({
    gridSize: size,
    initialGrid: cloneGrid(scrambledGrid),
    solutionMoves: [],
    allStates: [scrambledGrid],
    allMoves: [null],
    totalSteps: 0,
    gameMode: 'play',
    manualGrid: cloneGrid(scrambledGrid),
    playerMoves: 0,
    minimumMoves: null,
    step: 0,
    isPlaying: false,
    currentGrid: cloneGrid(scrambledGrid),
    prevGrid: null,
    currentMove: null,
    isSolved: isGridSolved(scrambledGrid),
    elapsedSeconds: 0,
    error: null,
  }));

  void dispatch(refreshMinimumMoves(scrambledGrid));
};
~~~

Spiegazione:
- usa la griglia generata dal backend come nuovo stato iniziale.
- aggiorna subito la UI senza attendere altre chiamate (snappy UX).
- azzera soluzione e replay perche sei in gioco manuale.
- calcola in background le mosse minime.
- _moves e' mantenuto per compatibilita firma, ma non usato qui.

### 6.9 setSolutionMovesFromApi(moves)

~~~ts
export const setSolutionMovesFromApi = (moves: Direction[]): PuzzleThunk => (dispatch, getState) => {
  const { initialGrid } = getState().puzzle;
  const { grids, moves: allMoves } = computeAllStates(initialGrid, moves);

  dispatch(patchState({
    solutionMoves: [...moves],
    allStates: grids,
    allMoves,
    totalSteps: grids.length - 1,
    gameMode: 'replay',
    playerMoves: 0,
    step: 0,
    isPlaying: false,
    currentGrid: grids[0],
    prevGrid: null,
    currentMove: allMoves[0],
    isSolved: false,
  }));
};
~~~

Spiegazione:
- prende una sequenza mosse (di solito da API solve).
- ricostruisce tutte le griglie del replay.
- passa modalita in replay.
- parte da step 0 con autoplay fermo (utente decide quando partire).

### 6.10 Pattern comune utile da ricordare

- Ogni thunk legge lo stato con getState().puzzle.
- Quasi tutte le funzioni usano guard-clause iniziali per evitare stati invalidi.
- Gli update avvengono con dispatch(patchState(...)).
- Le chiamate backend stanno nei thunk, non nei componenti: UI piu pulita.
- Se una funzione puo fallire per rete, c'e sempre gestione errori dedicata.
