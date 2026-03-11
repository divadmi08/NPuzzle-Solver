import { create } from 'zustand';
import type { Direction, Grid } from '../types/puzzle';
import { DEFAULT_SPEED } from '../constants/puzzle';
import { computeAllStates } from '../utils/puzzle';

const { grids, moves } = computeAllStates();

interface PuzzleState {
  // Dati pre-calcolati
  allStates: Grid[];
  allMoves: (Direction | null)[];
  totalSteps: number;

  // Stato corrente
  step: number;
  isPlaying: boolean;
  speed: number;

  // Derivati
  currentGrid: Grid;
  prevGrid: Grid | null;
  currentMove: Direction | null;
  isSolved: boolean;

  // Azioni
  setStep: (step: number) => void;
  goNext: () => void;
  goPrev: () => void;
  reset: () => void;
  play: () => void;
  pause: () => void;
  togglePlay: () => void;
  setSpeed: (speed: number) => void;
  jumpToStep: (step: number) => void;
  tick: () => boolean; // restituisce true se ha ancora passi
}

export const usePuzzleStore = create<PuzzleState>((set, get) => ({
  // Dati pre-calcolati
  allStates: grids,
  allMoves: moves,
  totalSteps: grids.length - 1,

  // Stato iniziale
  step: 0,
  isPlaying: false,
  speed: DEFAULT_SPEED,

  // Derivati
  currentGrid: grids[0],
  prevGrid: null,
  currentMove: null,
  isSolved: false,

  // Azioni
  setStep: (step) => {
    const { allStates, allMoves, totalSteps } = get();
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
    const { step, totalSteps } = get();
    if (step < totalSteps) get().setStep(step + 1);
  },

  goPrev: () => {
    const { step } = get();
    if (step > 0) get().setStep(step - 1);
  },

  reset: () => {
    get().pause();
    get().setStep(0);
  },

  play: () => {
    const { step, totalSteps } = get();
    if (step >= totalSteps) get().setStep(0);
    set({ isPlaying: true });
  },

  pause: () => {
    set({ isPlaying: false });
  },

  togglePlay: () => {
    const { isPlaying } = get();
    if (isPlaying) get().pause();
    else get().play();
  },

  setSpeed: (speed) => set({ speed }),

  jumpToStep: (step) => {
    get().pause();
    get().setStep(step);
  },

  tick: () => {
    const { step, totalSteps } = get();
    if (step >= totalSteps) {
      get().pause();
      return false;
    }
    get().setStep(step + 1);
    return true;
  },
}));
