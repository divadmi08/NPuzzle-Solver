import type { Direction, Grid } from '@/features/puzzle/types/puzzle';
import { DIRECTION_DELTAS } from '@/features/puzzle/constants/puzzle';

/**
 * Valida se una griglia ha dimensioni corrette e contiene numeri validi.
 */
function validateGrid(grid: Grid, expectedSize?: number): boolean {
  if (!Array.isArray(grid) || grid.length === 0) return false;
  const size = expectedSize ?? grid.length;
  if (grid.length !== size) return false;

  for (const row of grid) {
    if (!Array.isArray(row) || row.length !== size) return false;
    for (const cell of row) {
      if (typeof cell !== 'number' || cell < 0 || cell >= size * size) return false;
    }
  }
  return true;
}

/**
 * Trova la posizione dello zero (casella vuota) nella griglia.
 * Lancia un errore se la griglia è invalida o zero non viene trovato.
 */
export function findZero(grid: Grid): [number, number] {
  if (!validateGrid(grid)) {
    console.error('Invalid grid:', grid);
    throw new Error('Grid must be square and contain valid tile values');
  }

  const size = grid.length;

  for (let r = 0; r < size; r++) {
    for (let c = 0; c < size; c++) {
      if (grid[r][c] === 0) return [r, c];
    }
  }

  console.error('Zero not found in grid:', grid);
  throw new Error('No zero (empty cell) found in grid');
}

/**
 * Applica una mossa alla griglia, restituendo una nuova griglia.
 */
export function applyMove(grid: Grid, dir: Direction): Grid {
  const newGrid = grid.map(row => [...row]);
  const [r, c] = findZero(newGrid);
  const size = newGrid.length;
  const [dr, dc] = DIRECTION_DELTAS[dir];
  const nr = r + dr;
  const nc = c + dc;
  if (nr >= 0 && nr < size && nc >= 0 && nc < size) {
    [newGrid[r][c], newGrid[nr][nc]] = [newGrid[nr][nc], newGrid[r][c]];
  }
  return newGrid;
}

/**
 * Calcola tutti gli stati intermedi dalla griglia iniziale applicando tutte le mosse.
 * Valida la griglia iniziale e registra errori se si verificano.
 */
export function computeAllStates(initialGrid: Grid, movesSequence: Direction[]): {
  grids: Grid[];
  moves: (Direction | null)[];
} {
  if (!validateGrid(initialGrid)) {
    throw new Error('Initial grid must be square and valid');
  }

  const grids: Grid[] = [initialGrid.map(row => [...row])];
  const moves: (Direction | null)[] = [null];
  let current = initialGrid.map(row => [...row]);

  try {
    for (const move of movesSequence) {
      current = applyMove(current, move);
      grids.push(current);
      moves.push(move);
    }
  } catch (error) {
    console.error('Error computing puzzle states:', error);
    throw error;
  }

  return { grids, moves };
}

/**
 * Determina quale tessera è stata appena spostata confrontando due stati.
 */
export function getMovedTile(currentGrid: Grid, prevGrid: Grid | null): number {
  if (!prevGrid) return -1;
  try {
    const [r0, c0] = findZero(prevGrid);
    return currentGrid[r0][c0];
  } catch (error) {
    console.error('Error getting moved tile:', error);
    return -1;
  }
}

/**
 * Serializza una griglia in stringa leggibile.
 */
export function gridToString(grid: Grid): string {
  if (!validateGrid(grid)) {
    return '[invalid grid]';
  }
  return grid.map(r => `[${r.join(',')}]`).join(',');
}
