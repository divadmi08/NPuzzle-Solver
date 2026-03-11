import type { Direction, DirectionInfo, Grid } from '../types/puzzle';

export const GRID_SIZE = 4;

export const INITIAL_GRID: Grid = [
  [7, 2, 0, 8],
  [3, 11, 14, 5],
  [6, 15, 10, 1],
  [9, 12, 13, 4],
];

export const MOVES: Direction[] = [
  'DESTRA', 'SOTTO', 'SOTTO', 'SINISTRA', 'SINISTRA', 'SOTTO',
  'DESTRA', 'SOPRA', 'SOPRA', 'DESTRA', 'SOTTO', 'SOTTO',
  'SINISTRA', 'SOPRA', 'SINISTRA', 'SOPRA', 'DESTRA', 'DESTRA',
  'SOTTO', 'SINISTRA', 'SINISTRA', 'SINISTRA', 'SOTTO', 'DESTRA',
  'DESTRA', 'SOPRA', 'SOPRA', 'SOPRA', 'DESTRA', 'SOTTO',
  'SINISTRA', 'SOPRA', 'SINISTRA', 'SINISTRA', 'SOTTO', 'DESTRA',
  'DESTRA', 'SOPRA', 'SINISTRA', 'SINISTRA', 'SOTTO', 'DESTRA',
  'DESTRA', 'SOPRA', 'SINISTRA', 'SOTTO', 'SOTTO', 'DESTRA',
  'SOTTO', 'DESTRA',
];

export const DIRECTION_LABELS: Record<Direction, DirectionInfo> = {
  SINISTRA: { icon: '←', en: 'LEFT' },
  DESTRA:   { icon: '→', en: 'RIGHT' },
  SOPRA:    { icon: '↑', en: 'UP' },
  SOTTO:    { icon: '↓', en: 'DOWN' },
};

export const DIRECTION_DELTAS: Record<Direction, [number, number]> = {
  SINISTRA: [0, -1],
  DESTRA:   [0,  1],
  SOPRA:    [-1, 0],
  SOTTO:    [ 1, 0],
};

export const TILE_COLORS: Record<number, string> = {
  1:  'from-blue-500 to-blue-600',
  2:  'from-emerald-500 to-emerald-600',
  3:  'from-amber-500 to-amber-600',
  4:  'from-purple-500 to-purple-600',
  5:  'from-rose-500 to-rose-600',
  6:  'from-cyan-500 to-cyan-600',
  7:  'from-orange-500 to-orange-600',
  8:  'from-indigo-500 to-indigo-600',
  9:  'from-teal-500 to-teal-600',
  10: 'from-pink-500 to-pink-600',
  11: 'from-lime-500 to-lime-600',
  12: 'from-fuchsia-500 to-fuchsia-600',
  13: 'from-sky-500 to-sky-600',
  14: 'from-red-500 to-red-600',
  15: 'from-violet-500 to-violet-600',
};

export const DEFAULT_SPEED = 400;
export const MIN_SPEED = 100;
export const MAX_SPEED = 1500;
export const SPEED_STEP = 50;
