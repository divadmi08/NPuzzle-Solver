"use client";

import Link from 'next/link';
import { useMemo, useState } from 'react';
import type { Direction, GridSize } from '@/features/puzzle/types/puzzle';
import { useSetCustomBoard, useSetSolutionMovesFromApi } from '@/features/puzzle/store/puzzleSelectors';
import { useRouter } from 'next/navigation';

const VALID_DIRECTIONS: Direction[] = ['SINISTRA', 'DESTRA', 'SOPRA', 'SOTTO'];

function createEmptyGrid(size: GridSize): number[][] {
  return Array.from({ length: size }, () => Array.from({ length: size }, () => 0));
}

function parseCell(value: string): number {
  const num = Number(value.trim());
  return Number.isNaN(num) ? -1 : num;
}

function validateGridValues(values: number[], size: GridSize): string | null {
  const maxValue = size * size - 1;

  if (values.some((v) => v < 0 || v > maxValue)) {
    return `I valori devono essere compresi tra 0 e ${maxValue}.`;
  }

  const uniq = new Set(values);
  if (uniq.size !== values.length) {
    return 'Ogni numero deve comparire una sola volta.';
  }

  for (let n = 0; n <= maxValue; n++) {
    if (!uniq.has(n)) {
      return `Manca il numero ${n}.`;
    }
  }

  return null;
}

function toMatrix(values: number[], size: GridSize): number[][] {
  const matrix: number[][] = [];
  for (let r = 0; r < size; r++) {
    matrix.push(values.slice(r * size, (r + 1) * size));
  }
  return matrix;
}

function normalizeMoves(rawMoves: unknown): Direction[] {
  if (!Array.isArray(rawMoves)) return [];
  const parsed = rawMoves
    .map((m) => String(m).toUpperCase().trim())
    .filter((m): m is Direction => VALID_DIRECTIONS.includes(m as Direction));
  return parsed;
}

export default function CustomBoardPage() {
  const router = useRouter();
  const setCustomBoard = useSetCustomBoard();
  const setSolutionMovesFromApi = useSetSolutionMovesFromApi();

  const [gridSize, setGridSize] = useState<GridSize>(4);
  const [cellValues, setCellValues] = useState<string[]>(Array.from({ length: 16 }, (_, i) => String(i)));
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [feedback, setFeedback] = useState<string | null>(null);

  const maxValue = useMemo(() => gridSize * gridSize - 1, [gridSize]);

  const handleSizeChange = (size: GridSize) => {
    setGridSize(size);
    setCellValues(Array.from({ length: size * size }, (_, i) => String(i)));
    setFeedback(null);
  };

  const updateCell = (index: number, value: string) => {
    setCellValues((prev) => {
      const next = [...prev];
      next[index] = value;
      return next;
    });
  };

  const buildGrid = (): number[][] | null => {
    const numbers = cellValues.map(parseCell);
    const error = validateGridValues(numbers, gridSize);
    if (error) {
      setFeedback(error);
      return null;
    }
    return toMatrix(numbers, gridSize);
  };

  const handleUseBoard = () => {
    const grid = buildGrid();
    if (!grid) return;

    setCustomBoard(gridSize, grid);
    setFeedback('Tabella salvata. Ora puoi giocare o avviare la soluzione.');
    router.push('/game');
  };

  const handleResolveWithApi = async () => {
    const grid = buildGrid();
    if (!grid) return;

    setIsSubmitting(true);
    setFeedback('Invio richiesta API in corso...');

    try {
      setCustomBoard(gridSize, grid);

      const response = await fetch('/api/solve', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ gridSize, grid }),
      });

      if (!response.ok) {
        throw new Error(`API solve error: ${response.status}`);
      }

      const payload = await response.json();
      const moves = normalizeMoves(payload?.moves);
      if (moves.length === 0) {
        throw new Error('API non ha restituito mosse valide.');
      }

      setSolutionMovesFromApi(moves);
      setFeedback(`Soluzione caricata: ${moves.length} mosse.`);
      router.push('/game');
    } catch {
      setFeedback('API non disponibile o risposta non valida. La tabella e stata salvata comunque.');
    } finally {
      setIsSubmitting(false);
    }
  };

  const cellsCount = gridSize * gridSize;

  return (
    <main className="h-full w-full overflow-y-auto px-4 py-5 sm:px-6 sm:py-8 text-white">
      <div className="mx-auto w-full max-w-4xl rounded-2xl border border-slate-700 bg-slate-900/80 p-5 sm:p-7 shadow-2xl">
        <div className="flex flex-wrap items-center justify-between gap-3">
          <h1 className="text-2xl sm:text-3xl font-bold">Tabella Manuale</h1>
          <div className="flex gap-2">
            <Link
              href="/"
              className="rounded-lg border border-gray-600 bg-gray-800 px-3 py-1.5 text-sm font-semibold hover:bg-gray-700"
            >
              Home
            </Link>
            <Link
              href="/game"
              className="rounded-lg border border-cyan-500/60 bg-cyan-700 px-3 py-1.5 text-sm font-semibold hover:bg-cyan-600"
            >
              Vai al Gioco
            </Link>
          </div>
        </div>

        <div className="mt-6 grid grid-cols-1 lg:grid-cols-[260px_minmax(0,1fr)] gap-5">
          <section className="rounded-xl border border-slate-700 bg-slate-800/70 p-4">
            <h2 className="text-sm font-semibold uppercase tracking-wide text-slate-300">Configurazione</h2>
            <div className="mt-3 flex gap-2">
              <button
                onClick={() => handleSizeChange(3)}
                className={`rounded-lg border px-3 py-2 text-sm font-semibold ${
                  gridSize === 3 ? 'border-emerald-400/70 bg-emerald-700' : 'border-slate-600 bg-slate-700'
                }`}
              >
                3 x 3
              </button>
              <button
                onClick={() => handleSizeChange(4)}
                className={`rounded-lg border px-3 py-2 text-sm font-semibold ${
                  gridSize === 4 ? 'border-emerald-400/70 bg-emerald-700' : 'border-slate-600 bg-slate-700'
                }`}
              >
                4 x 4
              </button>
            </div>

            <p className="mt-4 text-xs text-slate-400">
              Inserisci tutti i numeri da 0 a {maxValue} una sola volta. Lo 0 e la casella vuota.
            </p>

            <div className="mt-4 flex flex-col gap-2">
              <button
                onClick={handleUseBoard}
                disabled={isSubmitting}
                className="rounded-lg border border-sky-400/60 bg-sky-700 px-4 py-2 text-sm font-semibold hover:bg-sky-600 disabled:opacity-60"
              >
                Usa Questa Tabella
              </button>
              <button
                onClick={handleResolveWithApi}
                disabled={isSubmitting}
                className="rounded-lg border border-orange-400/60 bg-orange-600 px-4 py-2 text-sm font-semibold hover:bg-orange-500 disabled:opacity-60"
              >
                {isSubmitting ? 'Richiesta API...' : 'Risolvi Con API'}
              </button>
            </div>
          </section>

          <section className="rounded-xl border border-slate-700 bg-slate-800/70 p-4">
            <h2 className="text-sm font-semibold uppercase tracking-wide text-slate-300">Posizione Numeri</h2>
            <div
              className="mt-4 grid gap-2"
              style={{ gridTemplateColumns: `repeat(${gridSize}, minmax(0, 1fr))` }}
            >
              {Array.from({ length: cellsCount }).map((_, index) => (
                <input
                  key={index}
                  type="number"
                  min={0}
                  max={maxValue}
                  value={cellValues[index] ?? ''}
                  onChange={(e) => updateCell(index, e.target.value)}
                  className="h-12 rounded-lg border border-slate-600 bg-slate-900 px-3 text-center text-lg font-semibold text-white outline-none focus:border-cyan-400"
                />
              ))}
            </div>

            {feedback && (
              <p className="mt-4 rounded-lg border border-slate-700 bg-slate-900/70 px-3 py-2 text-sm text-slate-200">
                {feedback}
              </p>
            )}
          </section>
        </div>
      </div>
    </main>
  );
}
