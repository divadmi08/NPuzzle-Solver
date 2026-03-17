"use client";

import Link from 'next/link';
import { COLOR_PALETTE_LABELS } from '@/features/puzzle/constants/puzzle';
import type { ColorPaletteMode } from '@/features/puzzle/types/puzzle';
import {
  useColorPaletteMode,
  useSetColorPaletteMode,
  useThemeMode,
  useToggleThemeMode,
} from '@/features/puzzle/store/puzzleSelectors';

const COLOR_MODES: ColorPaletteMode[] = [
  'default',
  'protanopia',
  'deuteranopia',
  'tritanopia',
  'achromatopsia',
];

export default function SettingsPage() {
  const themeMode = useThemeMode();
  const toggleThemeMode = useToggleThemeMode();
  const colorPaletteMode = useColorPaletteMode();
  const setColorPaletteMode = useSetColorPaletteMode();

  return (
    <main className="w-full h-full overflow-y-auto px-4 py-5 sm:px-6 sm:py-8 text-white">
      <div className="mx-auto w-full max-w-3xl rounded-2xl border border-slate-700 bg-slate-900/80 p-5 sm:p-7 shadow-2xl">
        <div className="flex flex-wrap items-center justify-between gap-3">
          <h1 className="text-2xl sm:text-3xl font-bold">Impostazioni</h1>
          <div className="flex items-center gap-2">
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
              Torna al Gioco
            </Link>
          </div>
        </div>

        <div className="mt-6 space-y-5">
          <section className="rounded-xl border border-slate-700 bg-slate-800/70 p-4">
            <h2 className="text-sm font-semibold uppercase tracking-wide text-slate-300">Tema</h2>
            <p className="mt-1 text-xs text-slate-400">Mantieni il tema scuro o attiva quello chiaro.</p>
            <button
              onClick={toggleThemeMode}
              className="mt-3 rounded-lg border border-sky-400/60 bg-sky-700 px-4 py-2 text-sm font-semibold hover:bg-sky-600"
            >
              {themeMode === 'dark' ? 'Passa a Modalita Chiara' : 'Passa a Modalita Scura'}
            </button>
          </section>

          <section className="rounded-xl border border-slate-700 bg-slate-800/70 p-4">
            <h2 className="text-sm font-semibold uppercase tracking-wide text-slate-300">Colori Tabella</h2>
            <p className="mt-1 text-xs text-slate-400">Scegli il profilo: standard o uno dei principali tipi di daltonismo.</p>
            <div className="mt-3 flex flex-wrap gap-2">
              {COLOR_MODES.map((mode) => (
                <button
                  key={mode}
                  onClick={() => setColorPaletteMode(mode)}
                  className={`rounded-lg border px-3 py-2 text-sm font-semibold ${
                    colorPaletteMode === mode
                      ? 'border-emerald-400/70 bg-emerald-700'
                      : 'border-slate-600 bg-slate-700'
                  }`}
                >
                  {COLOR_PALETTE_LABELS[mode]}
                </button>
              ))}
            </div>
          </section>
        </div>
      </div>
    </main>
  );
}
