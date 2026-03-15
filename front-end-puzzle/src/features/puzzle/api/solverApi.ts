import type { Direction, Grid, GridSize, PuzzleConfig } from '@/features/puzzle/types/puzzle';
const BACKEND_API_URL: string = process.env.BACKEND_API_URL || 'http://localhost:8080/puzzle';

function isGrid(value: unknown): value is Grid {
  return Array.isArray(value) && value.every((row) => Array.isArray(row) && row.every((cell) => typeof cell === 'number'));
}

function isMoves(value: unknown): value is Direction[] {
  return Array.isArray(value) && value.every((move) => typeof move === 'string');
}


export async function postSolvePuzzle(currentGrid: Grid): Promise<Direction[]> {
  const controller = new AbortController();
  const timeoutId = setTimeout(() => controller.abort(), 20000); // Timeout di 20 secondi

  try {
    const response = await fetch(BACKEND_API_URL + '/solve', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ "grid": currentGrid }),
      signal: controller.signal,
    });

    if (!response.ok) {
      throw new Error(`API solve error: ${response.status}`);
    }

    const payload = await response.json();
    return payload?.moves || [];
  } finally {
    clearTimeout(timeoutId);
  }
}

export async function postGeneratePuzzle(gridSize: GridSize): Promise<PuzzleConfig> {
  const controller = new AbortController();
  const timeoutId = setTimeout(() => controller.abort(), 20000);

  try {
    const response = await fetch(BACKEND_API_URL + '/generate', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({"size": gridSize }),
      signal: controller.signal,
    });

    if (!response.ok) {
      throw new Error(`API generate error: ${response.status}`);
    }

    const payload = await response.json();
    const initialGrid = payload?.initialGrid ?? payload?.grid;
    const moves = payload?.moves;

    if (!isGrid(initialGrid) || !isMoves(moves)) {
      throw new Error('API generate payload is invalid');
    }

    return {
      gridSize,
      initialGrid,
      moves,
    };
  } finally {
    clearTimeout(timeoutId);
  }
}
