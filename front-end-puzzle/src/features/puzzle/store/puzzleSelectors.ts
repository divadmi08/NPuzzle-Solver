/**
 * Type-safe selectors for usePuzzleStore
 * Centralizes all Zustand selectors to avoid inline type casting
 */
import { usePuzzleStore } from './usePuzzleStore';

// State selectors
export const useGridSize = () => usePuzzleStore(s => s.gridSize);
export const useInitialGrid = () => usePuzzleStore(s => s.initialGrid);
export const useSolutionMoves = () => usePuzzleStore(s => s.solutionMoves);
export const useAllStates = () => usePuzzleStore(s => s.allStates);
export const useAllMoves = () => usePuzzleStore(s => s.allMoves);
export const useTotalSteps = () => usePuzzleStore(s => s.totalSteps);
export const useStep = () => usePuzzleStore(s => s.step);
export const useIsPlaying = () => usePuzzleStore(s => s.isPlaying);
export const useSpeed = () => usePuzzleStore(s => s.speed);
export const useElapsedSeconds = () => usePuzzleStore(s => s.elapsedSeconds);
export const useTimerEnabled = () => usePuzzleStore(s => s.timerEnabled);
export const useThemeMode = () => usePuzzleStore(s => s.themeMode);
export const useColorPaletteMode = () => usePuzzleStore(s => s.colorPaletteMode);
export const useMusicEnabled = () => usePuzzleStore(s => s.musicEnabled);
export const useCurrentGrid = () => usePuzzleStore(s => s.currentGrid);
export const usePrevGrid = () => usePuzzleStore(s => s.prevGrid);
export const useCurrentMove = () => usePuzzleStore(s => s.currentMove);
export const useIsSolved = () => usePuzzleStore(s => s.isSolved);

// Game mode selectors
export const useGameMode = () => usePuzzleStore(s => s.gameMode);
export const useManualGrid = () => usePuzzleStore(s => s.manualGrid);

// Replay action selectors
export const useSetStep = () => usePuzzleStore(s => s.setStep);
export const useGoNext = () => usePuzzleStore(s => s.goNext);
export const useGoPrev = () => usePuzzleStore(s => s.goPrev);
export const usePlay = () => usePuzzleStore(s => s.play);
export const usePause = () => usePuzzleStore(s => s.pause);
export const useTogglePlay = () => usePuzzleStore(s => s.togglePlay);
export const useSetSpeed = () => usePuzzleStore(s => s.setSpeed);
export const useJumpToStep = () => usePuzzleStore(s => s.jumpToStep);
export const useTick = () => usePuzzleStore(s => s.tick);

// Game mode action selectors
export const usePlayMove = () => usePuzzleStore(s => s.playMove);
export const useGiveUp = () => usePuzzleStore(s => s.giveUp);
export const useRestartGame = () => usePuzzleStore(s => s.restartGame);
export const useSetGridSize = () => usePuzzleStore(s => s.setGridSize);
export const useTickElapsed = () => usePuzzleStore(s => s.tickElapsed);
export const useResetElapsed = () => usePuzzleStore(s => s.resetElapsed);
export const useSetTimerEnabled = () => usePuzzleStore(s => s.setTimerEnabled);
export const useToggleTimerEnabled = () => usePuzzleStore(s => s.toggleTimerEnabled);
export const useSetThemeMode = () => usePuzzleStore(s => s.setThemeMode);
export const useToggleThemeMode = () => usePuzzleStore(s => s.toggleThemeMode);
export const useSetColorPaletteMode = () => usePuzzleStore(s => s.setColorPaletteMode);
export const useToggleMusicEnabled = () => usePuzzleStore(s => s.toggleMusicEnabled);
export const useSetCustomBoard = () => usePuzzleStore(s => s.setCustomBoard);
export const useSetSolutionMovesFromApi = () => usePuzzleStore(s => s.setSolutionMovesFromApi);
