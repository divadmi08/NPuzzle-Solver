import type { Direction, Grid } from '../../types/puzzle';
import { DIRECTION_DELTAS, GRID_SIZE, INITIAL_GRID, MOVES } from '../constants/puzzle';

/**
 * Trova la posizione dello zero (casella vuota) nella griglia.
 */
export function findZero(grid: Grid): [number, number] {
  for (let r = 0; r < GRID_SIZE; r++) {
    for (let c = 0; c < GRID_SIZE; c++) {
      if (grid[r][c] === 0) return [r, c];
    }
  }
  return [0, 0];
}

/**
 * Applica una mossa alla griglia, restituendo una nuova griglia.
 */
export function applyMove(grid: Grid, dir: Direction): Grid {
  const newGrid = grid.map(row => [...row]);
  const [r, c] = findZero(newGrid);
  const [dr, dc] = DIRECTION_DELTAS[dir];
  const nr = r + dr;
  const nc = c + dc;
  if (nr >= 0 && nr < GRID_SIZE && nc >= 0 && nc < GRID_SIZE) {
    [newGrid[r][c], newGrid[nr][nc]] = [newGrid[nr][nc], newGrid[r][c]];
  }
  return newGrid;
}

/**
 * Calcola tutti gli stati intermedi dalla griglia iniziale applicando tutte le mosse.
 */
export function computeAllStates(): {
  grids: Grid[];
  moves: (Direction | null)[];
} {
  const grids: Grid[] = [INITIAL_GRID];
  const moves: (Direction | null)[] = [null];
  let current = INITIAL_GRID;

  for (const move of MOVES) {
    current = applyMove(current, move);
    grids.push(current);
    moves.push(move);
  }

  return { grids, moves };
}

/**
 * Determina quale tessera è stata appena spostata confrontando due stati.
 */
export function getMovedTile(currentGrid: Grid, prevGrid: Grid | null): number {
  if (!prevGrid) return -1;
  const [r0, c0] = findZero(prevGrid);
  return currentGrid[r0][c0];
}

/**
 * Serializza una griglia in stringa leggibile.
 */
export function gridToString(grid: Grid): string {
  return grid.map(r => `[${r.join(',')}]`).join(',');
}
