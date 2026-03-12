import { create } from 'zustand';
import type { ColorPaletteMode, Direction, Grid, GridSize, ThemeMode } from '@/features/puzzle/types/puzzle';
import { DEFAULT_GRID_SIZE, DEFAULT_SPEED, PUZZLE_CONFIGS } from '@/features/puzzle/constants/puzzle';
import { computeAllStates, applyMove } from '@/features/puzzle/utils/puzzle';

const defaultConfig = PUZZLE_CONFIGS[DEFAULT_GRID_SIZE];
const { grids: defaultGrids, moves: defaultMoves } = computeAllStates(
  defaultConfig.initialGrid,
  defaultConfig.moves,
);

function cloneGrid(grid: Grid): Grid {
  return grid.map(row => [...row]);
}

function areGridsEqual(a: Grid, b: Grid): boolean {
  if (a.length !== b.length) return false;
  for (let r = 0; r < a.length; r++) {
    if (a[r].length !== b[r].length) return false;
    for (let c = 0; c < a[r].length; c++) {
      if (a[r][c] !== b[r][c]) return false;
    }
  }
  return true;
}

interface PuzzleState {
  // Config corrente
  gridSize: GridSize;
  initialGrid: Grid;
  solutionMoves: Direction[];

  // Dati pre-calcolati
  allStates: Grid[];
  allMoves: (Direction | null)[];
  totalSteps: number;

  // Modalità gioco
  gameMode: 'play' | 'replay';
  manualGrid: Grid;

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

  // Azioni - Replay
  setStep: (step: number) => void;
  goNext: () => void;
  goPrev: () => void;
  play: () => void;
  pause: () => void;
  togglePlay: () => void;
  setSpeed: (speed: number) => void;
  jumpToStep: (step: number) => void;
  tick: () => boolean;

  // Azioni - Game Mode
  playMove: (direction: Direction) => void;
  giveUp: () => void;
  restartGame: () => void;
  setGridSize: (size: GridSize) => void;
  tickElapsed: () => void;
  resetElapsed: () => void;
  setTimerEnabled: (enabled: boolean) => void;
  toggleTimerEnabled: () => void;
  setThemeMode: (mode: ThemeMode) => void;
  toggleThemeMode: () => void;
  setColorPaletteMode: (mode: ColorPaletteMode) => void;
  toggleMusicEnabled: () => void;

  // Custom board flow
  setCustomBoard: (size: GridSize, grid: Grid) => void;
  setSolutionMovesFromApi: (moves: Direction[]) => void;
}

export const usePuzzleStore = create<PuzzleState>((set, get) => ({
  // Config corrente
  gridSize: defaultConfig.gridSize,
  initialGrid: cloneGrid(defaultConfig.initialGrid),
  solutionMoves: [...defaultConfig.moves],

  // Dati pre-calcolati
  allStates: defaultGrids,
  allMoves: defaultMoves,
  totalSteps: defaultGrids.length - 1,

  // Modalità gioco - Inizia in 'play'
  gameMode: 'play',
  manualGrid: cloneGrid(defaultGrids[0]),

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

  // Azioni - Replay
  setStep: (step) => {
    const { allStates, allMoves, totalSteps, gameMode } = get();
    if (gameMode !== 'replay') return;

    const clamped = Math.max(0, Math.min(totalSteps, step));
    set({
      step: clamped,
      currentGrid: allStates[clamped],
      prevGrid: clamped > 0 ? allStates[clamped - 1] : null,
      currentMove: allMoves[clamped],
      isSolved: clamped === totalSteps,
    });
  },

  goNext: () => {
    const { step, totalSteps, gameMode } = get();
    if (gameMode !== 'replay' || step >= totalSteps) return;
    get().setStep(step + 1);
  },

  goPrev: () => {
    const { step, gameMode } = get();
    if (gameMode !== 'replay' || step <= 0) return;
    get().setStep(step - 1);
  },

  play: () => {
    const { step, totalSteps, gameMode } = get();
    if (gameMode !== 'replay') return;
    if (step >= totalSteps) get().setStep(0);
    set({ isPlaying: true });
  },

  pause: () => {
    set({ isPlaying: false });
  },

  togglePlay: () => {
    const { isPlaying, gameMode } = get();
    if (gameMode !== 'replay') return;
    if (isPlaying) get().pause();
    else get().play();
  },

  setSpeed: (speed) => set({ speed }),

  jumpToStep: (step) => {
    const { gameMode } = get();
    if (gameMode !== 'replay') return;
    get().pause();
    get().setStep(step);
  },

  tick: () => {
    const { step, totalSteps, gameMode } = get();
    if (gameMode !== 'replay' || step >= totalSteps) {
      get().pause();
      return false;
    }
    get().setStep(step + 1);
    return true;
  },

  // Azioni - Game Mode
  playMove: (direction: Direction) => {
    const { gameMode, manualGrid, allStates, totalSteps } = get();
    if (gameMode !== 'play') return;

    try {
      const newGrid = applyMove(manualGrid, direction);
      const solvedGrid = allStates[totalSteps];
      set({
        manualGrid: newGrid,
        isSolved: areGridsEqual(newGrid, solvedGrid),
      });
    } catch (error) {
      console.warn('Invalid move:', error);
    }
  },

  giveUp: () => {
    const { allStates, allMoves } = get();
    set({
      gameMode: 'replay',
      step: 0,
      currentGrid: allStates[0],
      prevGrid: null,
      currentMove: allMoves[0],
      isPlaying: false,
    });
  },

  restartGame: () => {
    const { allStates } = get();
    set({
      gameMode: 'play',
      manualGrid: cloneGrid(allStates[0]),
      step: 0,
      isPlaying: false,
      currentGrid: cloneGrid(allStates[0]),
      prevGrid: null,
      currentMove: null,
      isSolved: false,
      elapsedSeconds: 0,
    });
  },

  setGridSize: (size: GridSize) => {
    const config = PUZZLE_CONFIGS[size];
    const { grids, moves } = computeAllStates(config.initialGrid, config.moves);

    set({
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
    });
  },

  tickElapsed: () => {
    const { gameMode } = get();
    if (gameMode !== 'play') return;
    set(state => ({ elapsedSeconds: state.elapsedSeconds + 1 }));
  },

  resetElapsed: () => {
    set({ elapsedSeconds: 0 });
  },

  setTimerEnabled: (enabled: boolean) => {
    set({ timerEnabled: enabled });
  },

  toggleTimerEnabled: () => {
    set(state => ({ timerEnabled: !state.timerEnabled }));
  },

  setThemeMode: (mode: ThemeMode) => {
    set({ themeMode: mode });
  },

  toggleThemeMode: () => {
    set(state => ({ themeMode: state.themeMode === 'dark' ? 'light' : 'dark' }));
  },

  setColorPaletteMode: (mode: ColorPaletteMode) => {
    set({ colorPaletteMode: mode });
  },

  toggleMusicEnabled: () => {
    set(state => ({ musicEnabled: !state.musicEnabled }));
  },

  setCustomBoard: (size: GridSize, grid: Grid) => {
    const customGrid = cloneGrid(grid);
    set({
      gridSize: size,
      initialGrid: customGrid,
      solutionMoves: [],
      allStates: [customGrid],
      allMoves: [null],
      totalSteps: 0,
      gameMode: 'play',
      manualGrid: cloneGrid(customGrid),
      step: 0,
      isPlaying: false,
      currentGrid: cloneGrid(customGrid),
      prevGrid: null,
      currentMove: null,
      isSolved: false,
      elapsedSeconds: 0,
    });
  },

  setSolutionMovesFromApi: (moves: Direction[]) => {
    const { initialGrid } = get();
    const { grids, moves: allMoves } = computeAllStates(initialGrid, moves);

    set({
      solutionMoves: [...moves],
      allStates: grids,
      allMoves,
      totalSteps: grids.length - 1,
      gameMode: 'replay',
      step: 0,
      isPlaying: false,
      currentGrid: grids[0],
      prevGrid: null,
      currentMove: allMoves[0],
      isSolved: false,
    });
  },
}));

// Usabile anche fuori da React, ad esempio nelle API request builders.
export const getGridSizeForApi = (): GridSize => usePuzzleStore.getState().gridSize;
