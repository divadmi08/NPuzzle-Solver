"use client";

import Link from 'next/link';
import { useEffect, useMemo, useRef, useState } from 'react';
import { COLOR_PALETTE_LABELS } from '@/features/puzzle/constants/puzzle';
import { getApiErrorMessage, postGeneratePuzzle } from '@/features/puzzle/api/solverApi';
import {
  useColorPaletteMode,
  useElapsedSeconds,
  useGameMode,
  useGiveUp,
  useGridSize,
  useInitialGrid,
  useMinimumMoves,
  useMusicEnabled,
  usePlayerMoves,
  useRefreshMinimumMoves,
  useRestartGame,
  useSetGeneratedPuzzle,
  useSetGridSize,
  useTimerEnabled,
  useThemeMode,
  useTickElapsed,
  useToggleTimerEnabled,
  useToggleThemeMode,
} from '@/features/puzzle/store/puzzleSelectors';

function formatTime(totalSeconds: number): string {
  const mins = Math.floor(totalSeconds / 60);
  const secs = totalSeconds % 60;
  return `${String(mins).padStart(2, '0')}:${String(secs).padStart(2, '0')}`;
}

export default function GamePlayNavbar() {
  const gameMode = useGameMode();
  const elapsedSeconds = useElapsedSeconds();
  const gridSize = useGridSize();
  const initialGrid = useInitialGrid();
  const playerMoves = usePlayerMoves();
  const minimumMoves = useMinimumMoves();
  const refreshMinimumMoves = useRefreshMinimumMoves();
  const setGridSize = useSetGridSize();
  const setGeneratedPuzzle = useSetGeneratedPuzzle();
  const tickElapsed = useTickElapsed();
  const giveUp = useGiveUp();
  const restartGame = useRestartGame();
  const timerEnabled = useTimerEnabled();
  const toggleTimerEnabled = useToggleTimerEnabled();
  const themeMode = useThemeMode();
  const toggleThemeMode = useToggleThemeMode();
  const colorPaletteMode = useColorPaletteMode();
  const musicEnabled = useMusicEnabled();
  const [selectedGridSize, setSelectedGridSize] = useState<3 | 4>(gridSize);
  const [gridLoading, setGridLoading] = useState(false);
  const [surrenderLoading, setSurrenderLoading] = useState(false);
  const [gridFeedback, setGridFeedback] = useState<string | null>(null);
  const initialMinimumMovesRequestedRef = useRef(false);
  const minMovesLoading = gameMode === 'play' && minimumMoves === null;

  useEffect(() => {
    if (gameMode !== 'play' || !timerEnabled) return;

    const id = setInterval(() => {
      tickElapsed();
    }, 1000);

    return () => clearInterval(id);
  }, [gameMode, timerEnabled, tickElapsed]);

  useEffect(() => {
    setSelectedGridSize(gridSize);
  }, [gridSize]);

  useEffect(() => {
    if (initialMinimumMovesRequestedRef.current) return;
    initialMinimumMovesRequestedRef.current = true;
    void refreshMinimumMoves(initialGrid);
  }, [initialGrid, refreshMinimumMoves]);

  const formattedTime = useMemo(() => formatTime(elapsedSeconds), [elapsedSeconds]);

  const handleGridSizeChange = async (nextGridSize: 3 | 4) => {
    setGridLoading(true);
    setGridFeedback('Caricamento puzzle da API...');

    try {
      const generatedPuzzle = await postGeneratePuzzle(nextGridSize);
      await setGeneratedPuzzle(nextGridSize, generatedPuzzle.initialGrid, generatedPuzzle.moves);
      setGridFeedback(null);
    } catch (err) {
      setGridSize(nextGridSize);
      const message = getApiErrorMessage(err, 'API non disponibile.');
      setGridFeedback(`${message} Caricata configurazione locale.`);
    } finally {
      setGridLoading(false);
    }
  };

  const handleGiveUp = async () => {
    setSurrenderLoading(true);
    setGridFeedback(null);

    try {
      await giveUp();
      setGridFeedback(null);
    } catch (err) {
      const message = getApiErrorMessage(err, 'API solve non disponibile.');
      setGridFeedback(`${message} Nessuna soluzione ricevuta.`);
    } finally {
      setSurrenderLoading(false);
    }
  };

  return (
    <>
      <nav className="mx-3 mb-2 sm:mx-4 sm:mb-3 rounded-xl border border-cyan-500/30 bg-slate-900/80 px-3 py-2 sm:px-4 sm:py-3 shadow-lg shadow-cyan-900/40">
        <div className="flex flex-wrap items-center justify-between gap-2 sm:gap-3">
        <div className="flex items-center gap-2">
          <Link
            href="/"
            className="rounded-lg border border-gray-600 bg-gray-800 px-3 py-1.5 text-xs sm:text-sm text-gray-100 hover:bg-gray-700"
          >
            Home
          </Link>
          <Link
            href="/settings"
            className="rounded-lg border border-gray-600 bg-gray-800 px-3 py-1.5 text-xs sm:text-sm text-gray-100 hover:bg-gray-700"
          >
            Impostazioni
          </Link>
          <Link
            href="/custom"
            className="rounded-lg border border-gray-600 bg-gray-800 px-3 py-1.5 text-xs sm:text-sm text-gray-100 hover:bg-gray-700"
          >
            Tabella Manuale
          </Link>
        </div>

        <div className="flex items-center gap-2 rounded-lg border border-gray-700/60 bg-gray-900/70 px-3 py-1.5">
          <span className="text-xs uppercase tracking-wide text-gray-400">Tempo</span>
          <span className="font-mono text-sm sm:text-base text-cyan-300">{formattedTime}</span>
        </div>

        <div className="flex items-center gap-2 rounded-lg border border-gray-700/60 bg-gray-900/70 px-3 py-1.5">
          <span className="text-xs uppercase tracking-wide text-gray-400">Mosse</span>
          <span className="font-mono text-sm sm:text-base text-emerald-300">
            {playerMoves}
            {minimumMoves === null ? ' / ...' : ` / ${minimumMoves}`}
          </span>
          {minMovesLoading && <span className="inline-block h-2 w-2 rounded-full bg-cyan-300 animate-pulse" aria-label="Calcolo mosse minime" title="Calcolo mosse minime in corso" />}
        </div>

        <div className="flex items-center gap-2 rounded-xl border border-cyan-500/30 bg-linear-to-r from-slate-900/80 via-slate-800/70 to-slate-900/80 px-2 py-1.5 shadow-md shadow-cyan-900/20">
          <label htmlFor="grid-size" className="text-xs uppercase tracking-wide text-gray-400">
            Tabella
          </label>
          <div className="relative">
            <select
              id="grid-size"
              value={selectedGridSize}
              onChange={(e) => {
                const nextGridSize = Number(e.target.value) as 3 | 4;
                setSelectedGridSize(nextGridSize);
                if (nextGridSize !== gridSize) {
                  void handleGridSizeChange(nextGridSize);
                }
              }}
              disabled={gridLoading}
              className="no-native-arrow rounded-lg border border-cyan-500/40 bg-slate-800/95 pl-3 pr-9 py-1.5 text-sm font-semibold text-cyan-100 outline-none transition-all focus:border-cyan-300 focus:ring-2 focus:ring-cyan-500/30 disabled:cursor-not-allowed disabled:opacity-60"
            >
              <option value={3}>3 x 3</option>
              <option value={4}>4 x 4</option>
            </select>
            <span className="pointer-events-none absolute inset-y-0 right-2 flex items-center text-cyan-200" aria-hidden="true">
              <svg viewBox="0 0 20 20" fill="currentColor" className="h-4 w-4">
                <path
                  fillRule="evenodd"
                  d="M5.23 7.21a.75.75 0 0 1 1.06.02L10 11.12l3.71-3.89a.75.75 0 1 1 1.08 1.04l-4.25 4.45a.75.75 0 0 1-1.08 0L5.21 8.27a.75.75 0 0 1 .02-1.06Z"
                  clipRule="evenodd"
                />
              </svg>
            </span>
          </div>
          <button
            onClick={() => {
              void handleGridSizeChange(selectedGridSize);
            }}
            disabled={gridLoading}
            className="rounded-lg border border-cyan-400/60 bg-cyan-700 px-3 py-1.5 text-xs sm:text-sm font-semibold text-white transition-colors hover:bg-cyan-600 disabled:opacity-60"
          >
            Rigenera
          </button>
          {gridLoading && <span className="text-[11px] sm:text-xs font-semibold text-cyan-300">Cambio...</span>}
        </div>

        <button
          onClick={toggleTimerEnabled}
          className="rounded-lg border border-amber-400/60 bg-amber-700 px-3 py-1.5 text-xs sm:text-sm font-semibold text-white transition-colors hover:bg-amber-600"
        >
          {timerEnabled ? 'Stop Timer' : 'Avvia Timer'}
        </button>

        <button
          onClick={toggleThemeMode}
          className="rounded-lg border border-sky-400/60 bg-sky-700 px-3 py-1.5 text-xs sm:text-sm font-semibold text-white transition-colors hover:bg-sky-600"
        >
          {themeMode === 'dark' ? 'Modalita Chiara' : 'Modalita Scura'}
        </button>

        {gameMode === 'play' ? (
          <button
            onClick={() => {
              void handleGiveUp();
            }}
            disabled={surrenderLoading}
            className="rounded-lg border border-orange-400/60 bg-orange-600 px-4 py-1.5 text-sm font-semibold text-white transition-colors hover:bg-orange-500"
          >
            {surrenderLoading ? 'Calcolo...' : 'Arrenditi'}
          </button>
        ) : (
          <button
            onClick={restartGame}
            className="rounded-lg border border-emerald-400/60 bg-emerald-600 px-4 py-1.5 text-sm font-semibold text-white transition-colors hover:bg-emerald-500"
          >
            Riprova
          </button>
        )}
        </div>

        <div className="mt-2 text-[11px] sm:text-xs text-gray-400">
          Tema: <span className="text-gray-200">{themeMode === 'dark' ? 'Scuro' : 'Chiaro'}</span> ·
          Palette: <span className="text-gray-200">{COLOR_PALETTE_LABELS[colorPaletteMode]}</span> ·
          Musica: <span className="text-gray-200">{musicEnabled ? 'Attiva' : 'Disattivata'}</span>
          {minMovesLoading ? ' · Calcolo mosse minime in corso...' : ''}
        </div>

        {gridFeedback && (
          <div className="mt-1 text-[11px] sm:text-xs text-amber-300">{gridFeedback}</div>
        )}
      </nav>

      {surrenderLoading && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 backdrop-blur-[2px]">
          <div className="rounded-xl border border-cyan-500/30 bg-slate-900/90 px-6 py-5 text-center shadow-2xl shadow-cyan-900/35">
            <div className="mx-auto h-10 w-10 rounded-full border-4 border-cyan-400/25 border-t-cyan-300 animate-spin" aria-hidden="true" />
            <p className="mt-3 text-sm font-semibold text-cyan-100">Calcolo soluzione in corso...</p>
          </div>
        </div>
      )}
    </>
  );
}
