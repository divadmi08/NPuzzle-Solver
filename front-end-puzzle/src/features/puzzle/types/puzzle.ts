export type Grid = number[][];
export type GridSize = 3 | 4;
export type ThemeMode = 'dark' | 'light';
export type ColorPaletteMode =
  | 'default'
  | 'protanopia'
  | 'deuteranopia'
  | 'tritanopia'
  | 'achromatopsia';

export type Direction = 'SINISTRA' | 'SOTTO' | 'SOPRA' | 'DESTRA';

export interface DirectionInfo {
  icon: string;
  en: string;
}

export interface PuzzleConfig {
  gridSize: GridSize;
  initialGrid: Grid;
  moves: Direction[];
}
