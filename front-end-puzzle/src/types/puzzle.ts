export type Grid = number[][];

export type Direction = 'SINISTRA' | 'SOTTO' | 'SOPRA' | 'DESTRA';

export interface DirectionInfo {
  icon: string;
  en: string;
}

export interface PuzzleConfig {
  gridSize: number;
  initialGrid: Grid;
  moves: Direction[];
}
