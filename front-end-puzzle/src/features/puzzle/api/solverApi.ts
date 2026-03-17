import type { Direction, Grid, GridSize, PuzzleConfig } from '@/features/puzzle/types/puzzle';
const BACKEND_API_URL: string = process.env.BACKEND_API_URL || 'http://localhost:8080/puzzle';
const REQUEST_TIMEOUT_MS = 20000;

export class ApiConnectionError extends Error {
  constructor(url: string, cause?: unknown) {
    super(`Cannot reach the server at "${url}". Check that the backend is running and the address is correct.`);
    this.name = 'ApiConnectionError';
    this.cause = cause;
  }
}

export class ApiTimeoutError extends Error {
  constructor(url: string) {
    super(`Request to "${url}" timed out. The server may be overloaded or unreachable.`);
    this.name = 'ApiTimeoutError';
  }
}

export class ApiHttpError extends Error {
  constructor(public readonly status: number, endpoint: string) {
    super(`Server returned HTTP ${status} for "${endpoint}".`);
    this.name = 'ApiHttpError';
  }
}

export class ApiPayloadError extends Error {
  constructor(endpoint: string) {
    super(`Unexpected response payload from "${endpoint}". The API contract may have changed.`);
    this.name = 'ApiPayloadError';
  }
}

export function isUnsolvableApiError(error: unknown): boolean {
  if (error instanceof ApiHttpError && error.status === 500) {
    return true;
  }

  if (error instanceof Error) {
    const message = error.message.toLowerCase();
    return message.includes('not solvable') || message.includes('non risolvibile');
  }

  return false;
}

export function getApiErrorMessage(error: unknown, fallbackMessage: string): string {
  if (isUnsolvableApiError(error)) {
    return 'Puzzle non risolvibile.';
  }
  if (error instanceof ApiConnectionError) {
    return 'Impossibile raggiungere il server backend.';
  }
  if (error instanceof ApiTimeoutError) {
    return 'Il server non ha risposto in tempo.';
  }
  if (error instanceof ApiHttpError) {
    return `Errore dal server (HTTP ${error.status}).`;
  }
  if (error instanceof ApiPayloadError) {
    return 'Risposta API non valida.';
  }
  return fallbackMessage;
}

function handleFetchError(error: unknown, url: string): never {
  if (error instanceof ApiConnectionError || error instanceof ApiTimeoutError || error instanceof ApiHttpError || error instanceof ApiPayloadError) {
    throw error;
  }
  if (error instanceof DOMException && error.name === 'AbortError') {
    throw new ApiTimeoutError(url);
  }
  // TypeError from fetch means a network/address-level failure (DNS, ECONNREFUSED, etc.)
  if (error instanceof TypeError) {
    throw new ApiConnectionError(url, error);
  }
  throw new Error(`Unexpected error: ${String(error)}`);
}

function isGrid(value: unknown): value is Grid {
  return Array.isArray(value) && value.every((row) => Array.isArray(row) && row.every((cell) => typeof cell === 'number'));
}

function isMoves(value: unknown): value is Direction[] {
  return Array.isArray(value) && value.every((move) => typeof move === 'string');
}

function isMinMoves(value: unknown): value is number {
  return typeof value === 'number' && Number.isInteger(value) && value >= 0;
}

async function postJson(path: string, body: unknown): Promise<{ endpoint: string; payload: unknown }> {
  const endpoint = BACKEND_API_URL + path;
  const controller = new AbortController();
  const timeoutId = setTimeout(() => controller.abort(), REQUEST_TIMEOUT_MS);

  try {
    const response = await fetch(endpoint, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
      signal: controller.signal,
    });

    if (!response.ok) {
      throw new ApiHttpError(response.status, endpoint);
    }

    let payload: unknown;
    try {
      payload = await response.json();
    } catch {
      throw new ApiPayloadError(endpoint);
    }

    return { endpoint, payload };
  } catch (error) {
    handleFetchError(error, endpoint);
  } finally {
    clearTimeout(timeoutId);
  }
}


export async function postSolvePuzzle(currentGrid: Grid): Promise<Direction[]> {
  const { endpoint, payload } = await postJson('/solve', { grid: currentGrid });
  const moves = (payload as { moves?: unknown })?.moves;
  if (!isMoves(moves)) {
    throw new ApiPayloadError(endpoint);
  }

  return moves;
}

export async function postGeneratePuzzle(gridSize: GridSize): Promise<PuzzleConfig> {
  const { endpoint, payload } = await postJson('/generate', { size: gridSize });
  const grid = (payload as { grid?: unknown })?.grid;

  if (!isGrid(grid)) {
    throw new ApiPayloadError(endpoint);
  }

  return {
    gridSize,
    initialGrid: grid,
    moves: [],
  };
}

export async function postMinMovesPuzzle(currentGrid: Grid): Promise<number> {
  const { endpoint, payload } = await postJson('/min-moves', { grid: currentGrid });
  const minMoves = (payload as { minMoves?: unknown })?.minMoves;
  if (!isMinMoves(minMoves)) {
    throw new ApiPayloadError(endpoint);
  }

  return minMoves;
}
