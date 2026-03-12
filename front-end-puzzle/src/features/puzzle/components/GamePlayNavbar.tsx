"use client";

import Link from 'next/link';
import { useEffect, useMemo } from 'react';
import { COLOR_PALETTE_LABELS } from '@/features/puzzle/constants/puzzle';
import {
  useColorPaletteMode,
  useElapsedSeconds,
  useGameMode,
  useGiveUp,
  useGridSize,
  useMusicEnabled,
  useRestartGame,
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
  const setGridSize = useSetGridSize();
  const tickElapsed = useTickElapsed();
  const giveUp = useGiveUp();
  const restartGame = useRestartGame();
  const timerEnabled = useTimerEnabled();
  const toggleTimerEnabled = useToggleTimerEnabled();
  const themeMode = useThemeMode();
  const toggleThemeMode = useToggleThemeMode();
  const colorPaletteMode = useColorPaletteMode();
  const musicEnabled = useMusicEnabled();

  useEffect(() => {
    if (gameMode !== 'play' || !timerEnabled) return;

    const id = setInterval(() => {
      tickElapsed();
    }, 1000);

    return () => clearInterval(id);
  }, [gameMode, timerEnabled, tickElapsed]);

  const formattedTime = useMemo(() => formatTime(elapsedSeconds), [elapsedSeconds]);

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
          <select
            id="grid-size"
            value={gridSize}
            onChange={(e) => setGridSize(Number(e.target.value) as 3 | 4)}
            className="rounded-lg border border-gray-600 bg-gray-800 px-2.5 py-1.5 text-sm text-gray-100 outline-none focus:border-cyan-400"
          >
            <option value={3}>3 x 3</option>
            <option value={4}>4 x 4</option>
          </select>
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
            onClick={giveUp}
            className="rounded-lg border border-orange-400/60 bg-orange-600 px-4 py-1.5 text-sm font-semibold text-white transition-colors hover:bg-orange-500"
          >
            Arrendi
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
    </nav>
  );
}
