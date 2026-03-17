"use client";

import Link from 'next/link';
import { useEffect, useMemo, useState } from 'react';
import { COLOR_PALETTE_LABELS } from '@/features/puzzle/constants/puzzle';
import { postGeneratePuzzle, postSolvePuzzle, ApiConnectionError, ApiTimeoutError, ApiHttpError, ApiPayloadError } from '@/features/puzzle/api/solverApi';
import {
  useColorPaletteMode,
  useCurrentGrid,
  useElapsedSeconds,
  useGameMode,
  useGiveUp,
  useGridSize,
  useMusicEnabled,
  useRestartGame,
  useSetGeneratedPuzzle,
  useSetGridSize,
  useSetSolutionMovesFromApi,
  useSolutionMoves,
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
  const currentGrid = useCurrentGrid();
  const solutionMoves = useSolutionMoves();
  const setGridSize = useSetGridSize();
  const setGeneratedPuzzle = useSetGeneratedPuzzle();
  const setSolutionMovesFromApi = useSetSolutionMovesFromApi();
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

  const formattedTime = useMemo(() => formatTime(elapsedSeconds), [elapsedSeconds]);

  const handleGridSizeChange = async () => {
    setGridLoading(true);
    setGridFeedback('Caricamento puzzle da API...');

    try {
      const generatedPuzzle = await postGeneratePuzzle(selectedGridSize);
      await setGeneratedPuzzle(selectedGridSize, generatedPuzzle.initialGrid, generatedPuzzle.moves);
      setGridFeedback(null);
    } catch (err) {
      setGridSize(selectedGridSize);
      if (err instanceof ApiConnectionError) {
        setGridFeedback('Impossibile raggiungere il server. Controlla che il backend sia avviato e che l\'indirizzo sia corretto. Caricata configurazione locale.');
      } else if (err instanceof ApiTimeoutError) {
        setGridFeedback('Il server non ha risposto in tempo. Caricata configurazione locale.');
      } else if (err instanceof ApiHttpError) {
        setGridFeedback(`Errore dal server (HTTP ${err.status}). Caricata configurazione locale.`);
      } else if (err instanceof ApiPayloadError) {
        setGridFeedback('Risposta del server non valida. Caricata configurazione locale.');
      } else {
        setGridFeedback('API non disponibile: caricata configurazione locale.');
      }
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
      if (err instanceof ApiConnectionError) {
        setGridFeedback('Impossibile raggiungere il server. Controlla che il backend sia avviato e che l\'indirizzo sia corretto.');
      } else if (err instanceof ApiTimeoutError) {
        setGridFeedback('Il server non ha risposto in tempo. Riprova più tardi.');
      } else if (err instanceof ApiHttpError) {
        setGridFeedback(`Errore dal server (HTTP ${err.status}). Nessuna soluzione ricevuta.`);
      } else if (err instanceof ApiPayloadError) {
        setGridFeedback('Risposta del server non valida. Nessuna soluzione ricevuta.');
      } else {
        setGridFeedback('API solve non disponibile: nessuna soluzione ricevuta.');
      }
    } finally {
      setSurrenderLoading(false);
    }
  };

  return (
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

        <div className="flex items-center gap-2">
          <label htmlFor="grid-size" className="text-xs uppercase tracking-wide text-gray-400">
            Tabella
          </label>
          <div className="relative">
            <select
              id="grid-size"
              value={selectedGridSize}
              onChange={(e) => setSelectedGridSize(Number(e.target.value) as 3 | 4)}
              disabled={gridLoading}
              className="no-native-arrow rounded-lg border border-gray-600 bg-gray-800 pl-2.5 pr-8 py-1.5 text-sm text-gray-100 outline-none focus:border-cyan-400"
            >
              <option value={3}>3 x 3</option>
              <option value={4}>4 x 4</option>
            </select>
            <span className="pointer-events-none absolute inset-y-0 right-2 flex items-center text-cyan-300" aria-hidden="true">
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
              void handleGridSizeChange();
            }}
            disabled={gridLoading}
            className="rounded-lg border border-cyan-400/60 bg-cyan-700 px-3 py-1.5 text-xs sm:text-sm font-semibold text-white transition-colors hover:bg-cyan-600 disabled:opacity-60"
          >
            {gridLoading ? 'Cambio...' : 'Cambia Tabella'}
          </button>
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
      </div>

      {gridFeedback && (
        <div className="mt-1 text-[11px] sm:text-xs text-amber-300">{gridFeedback}</div>
      )}
    </nav>
  );
}
