import type { Direction, Grid, GridSize, PuzzleConfig } from '@/features/puzzle/types/puzzle';
const BACKEND_API_URL: string = process.env.BACKEND_API_URL || 'http://localhost:8080/puzzle';

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


export async function postSolvePuzzle(currentGrid: Grid): Promise<Direction[]> {
  const endpoint = BACKEND_API_URL + '/solve';
  const controller = new AbortController();
  const timeoutId = setTimeout(() => controller.abort(), 20000);

  try {
    const response = await fetch(endpoint, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ "grid": currentGrid }),
      signal: controller.signal,
    });

    if (!response.ok) {
      throw new ApiHttpError(response.status, endpoint);
    }

    const payload = await response.json();
    return payload?.moves || [];
  } catch (error) {
    handleFetchError(error, endpoint);
  } finally {
    clearTimeout(timeoutId);
  }
}

export async function postGeneratePuzzle(gridSize: GridSize): Promise<PuzzleConfig> {
  const endpoint = BACKEND_API_URL + '/generate';
  const controller = new AbortController();
  const timeoutId = setTimeout(() => controller.abort(), 20000);

  try {
    const response = await fetch(endpoint, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({"size": gridSize }),
      signal: controller.signal,
    });

    if (!response.ok) {
      throw new ApiHttpError(response.status, endpoint);
    }

    const payload = await response.json();
    const grid = payload?.grid;

    if (!isGrid(grid)) {
      throw new ApiPayloadError(endpoint);
    }

    return {
      gridSize,
      initialGrid: grid,
      moves: [],
    };
  } catch (error) {
    handleFetchError(error, endpoint);
  } finally {
    clearTimeout(timeoutId);
  }
}
