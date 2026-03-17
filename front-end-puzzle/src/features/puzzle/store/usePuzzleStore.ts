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
