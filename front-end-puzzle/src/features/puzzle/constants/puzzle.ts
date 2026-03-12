import type { ColorPaletteMode, Direction, DirectionInfo, Grid, GridSize, PuzzleConfig } from '@/features/puzzle/types/puzzle';

const INITIAL_GRID_4X4: Grid = [
  [7, 2, 0, 8],
  [3, 11, 14, 5],
  [6, 15, 10, 1],
  [9, 12, 13, 4],
];

const MOVES_4X4: Direction[] = [
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

const INITIAL_GRID_3X3: Grid = [
  [1, 2, 3],
  [7, 0, 6],
  [5, 4, 8],
];

const MOVES_3X3: Direction[] = [
  'SOTTO',
  'SINISTRA',
  'SOPRA',
  'DESTRA',
  'SOTTO',
  'DESTRA',
];

export const PUZZLE_CONFIGS: Record<GridSize, PuzzleConfig> = {
  3: {
    gridSize: 3,
    initialGrid: INITIAL_GRID_3X3,
    moves: MOVES_3X3,
  },
  4: {
    gridSize: 4,
    initialGrid: INITIAL_GRID_4X4,
    moves: MOVES_4X4,
  },
};

export const DEFAULT_GRID_SIZE: GridSize = 4;

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

export const TILE_COLORS_PROTANOPIA: Record<number, string> = {
  1: 'from-sky-500 to-sky-600',
  2: 'from-cyan-500 to-cyan-600',
  3: 'from-yellow-500 to-yellow-600',
  4: 'from-violet-500 to-violet-600',
  5: 'from-slate-500 to-slate-600',
  6: 'from-indigo-500 to-indigo-600',
  7: 'from-amber-500 to-amber-600',
  8: 'from-blue-500 to-blue-600',
  9: 'from-emerald-500 to-emerald-600',
  10: 'from-zinc-500 to-zinc-600',
  11: 'from-lime-500 to-lime-600',
  12: 'from-orange-500 to-orange-600',
  13: 'from-fuchsia-500 to-fuchsia-600',
  14: 'from-stone-500 to-stone-600',
  15: 'from-teal-500 to-teal-600',
};

export const TILE_COLORS_DEUTERANOPIA: Record<number, string> = {
  1: 'from-blue-500 to-blue-600',
  2: 'from-cyan-500 to-cyan-600',
  3: 'from-amber-500 to-amber-600',
  4: 'from-purple-500 to-purple-600',
  5: 'from-zinc-500 to-zinc-600',
  6: 'from-sky-500 to-sky-600',
  7: 'from-yellow-500 to-yellow-600',
  8: 'from-indigo-500 to-indigo-600',
  9: 'from-teal-500 to-teal-600',
  10: 'from-rose-500 to-rose-600',
  11: 'from-lime-500 to-lime-600',
  12: 'from-fuchsia-500 to-fuchsia-600',
  13: 'from-orange-500 to-orange-600',
  14: 'from-stone-500 to-stone-600',
  15: 'from-emerald-500 to-emerald-600',
};

export const TILE_COLORS_TRITANOPIA: Record<number, string> = {
  1: 'from-red-500 to-red-600',
  2: 'from-orange-500 to-orange-600',
  3: 'from-yellow-500 to-yellow-600',
  4: 'from-rose-500 to-rose-600',
  5: 'from-lime-500 to-lime-600',
  6: 'from-emerald-500 to-emerald-600',
  7: 'from-green-500 to-green-600',
  8: 'from-zinc-500 to-zinc-600',
  9: 'from-stone-500 to-stone-600',
  10: 'from-amber-500 to-amber-600',
  11: 'from-fuchsia-500 to-fuchsia-600',
  12: 'from-pink-500 to-pink-600',
  13: 'from-indigo-500 to-indigo-600',
  14: 'from-slate-500 to-slate-600',
  15: 'from-violet-500 to-violet-600',
};

export const TILE_COLORS_ACHROMATOPSIA: Record<number, string> = {
  1: 'from-slate-500 to-slate-600',
  2: 'from-slate-600 to-slate-700',
  3: 'from-zinc-400 to-zinc-500',
  4: 'from-zinc-500 to-zinc-600',
  5: 'from-gray-400 to-gray-500',
  6: 'from-gray-500 to-gray-600',
  7: 'from-neutral-500 to-neutral-600',
  8: 'from-neutral-600 to-neutral-700',
  9: 'from-stone-400 to-stone-500',
  10: 'from-stone-500 to-stone-600',
  11: 'from-zinc-300 to-zinc-400',
  12: 'from-zinc-700 to-zinc-800',
  13: 'from-gray-300 to-gray-400',
  14: 'from-gray-600 to-gray-700',
  15: 'from-stone-600 to-stone-700',
};

export const TILE_COLORS_BY_MODE: Record<ColorPaletteMode, Record<number, string>> = {
  default: TILE_COLORS,
  protanopia: TILE_COLORS_PROTANOPIA,
  deuteranopia: TILE_COLORS_DEUTERANOPIA,
  tritanopia: TILE_COLORS_TRITANOPIA,
  achromatopsia: TILE_COLORS_ACHROMATOPSIA,
};

export const COLOR_PALETTE_LABELS: Record<ColorPaletteMode, string> = {
  default: 'Standard',
  protanopia: 'Protanopia',
  deuteranopia: 'Deuteranopia',
  tritanopia: 'Tritanopia',
  achromatopsia: 'Achromatopsia',
};

export const DEFAULT_SPEED = 400;
export const MIN_SPEED = 100;
export const MAX_SPEED = 1500;
export const SPEED_STEP = 50;
