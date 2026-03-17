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
