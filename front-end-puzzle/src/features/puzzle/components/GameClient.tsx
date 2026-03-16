"use client";

import { usePlayback } from '@/features/puzzle/hooks/usePlayback';
import Header from '@/features/puzzle/components/Header';
import MoveInfo from '@/features/puzzle/components/MoveInfo';
import PuzzleGrid from '@/features/puzzle/components/PuzzleGrid';
import Controls from '@/features/puzzle/components/Controls';
import SpeedControl from '@/features/puzzle/components/SpeedControl';
import ProgressBar from '@/features/puzzle/components/ProgressBar';
import MoveList from '@/features/puzzle/components/MoveList';
import StatesPreview from '@/features/puzzle/components/StatesPreview';
import Footer from '@/features/puzzle/components/Footer';
import GamePlayNavbar from '@/features/puzzle/components/GamePlayNavbar';
import { useGameMode, useThemeMode, useError, useClearError } from '@/features/puzzle/store/puzzleSelectors';

export default function App() {
  // Attiva il loop di autoplay
  usePlayback();
  const gameMode = useGameMode();
  const themeMode = useThemeMode();
  const error = useError();
  const clearError = useClearError();

  const rootThemeClass =
    themeMode === 'dark'
      ? 'bg-gradient-to-br from-gray-900 via-gray-800 to-gray-900 text-white'
      : 'bg-gradient-to-br from-slate-100 via-sky-100 to-blue-50 text-slate-900';

  const panelThemeClass =
    themeMode === 'dark'
      ? 'border-gray-700/60 bg-slate-900/80 shadow-xl shadow-black/30'
      : 'border-slate-300/80 bg-white/85 shadow-xl shadow-sky-200/40';

  const hintThemeClass =
    themeMode === 'dark'
      ? 'border-gray-700/60 bg-gray-900/60 text-gray-300'
      : 'border-slate-300/80 bg-slate-50/90 text-slate-700';

  const replayHintThemeClass = themeMode === 'dark' ? 'text-gray-400' : 'text-slate-500';

  return (
    <div className={`w-full h-full flex flex-col overflow-hidden ${rootThemeClass}`}>
      <div className="shrink-0 px-3 py-2 sm:px-4 sm:py-3">
        <Header />
      </div>

      <GamePlayNavbar />

      <div className="flex-1 min-h-0 px-3 pb-2 sm:px-4 sm:pb-4 lg:px-6">
        <div className="h-full min-h-0 grid grid-cols-1 xl:grid-cols-[280px_minmax(0,1fr)_340px] gap-4">
          <aside className={`order-2 xl:order-1 min-h-0 rounded-xl border p-3 sm:p-4 overflow-y-auto ${panelThemeClass}`}>
            {gameMode === 'replay' ? (
              <div className="flex flex-col gap-3">
                <Controls />
                <MoveInfo />
                <SpeedControl />
                <ProgressBar />
              </div>
            ) : (
              <div className={`rounded-lg border p-3 text-sm leading-relaxed ${hintThemeClass}`}>
                Clicca le tessere adiacenti allo spazio vuoto per muovere il puzzle.
              </div>
            )}
          </aside>

          <main className="order-1 xl:order-2 min-h-0 flex items-center justify-center overflow-hidden">
            <PuzzleGrid />
          </main>

          <aside className={`order-3 min-h-0 rounded-xl border p-3 sm:p-4 overflow-y-auto ${panelThemeClass}`}>
            {gameMode === 'replay' ? (
              <div className="flex flex-col gap-3">
                <MoveList />
                <StatesPreview />
                <Footer />
              </div>
            ) : (
              <div className={`h-full flex items-center justify-center text-center text-xs sm:text-sm leading-relaxed ${replayHintThemeClass}`}>
                Se non riesci, premi Arrendi nella barra sopra per avviare la soluzione animata.
              </div>
            )}
          </aside>
        </div>
      </div>

      {error && (
        <div className="fixed inset-0 flex items-center justify-center z-50">
          <div className="bg-black/50 rounded-xl sm:rounded-2xl backdrop-blur-[3px] p-6 sm:p-8 text-center">
            <div className="text-4xl sm:text-5xl mb-2">❌</div>
            <div className="text-lg sm:text-xl font-bold text-red-400 whitespace-pre-wrap mb-4">{error}</div>
            <button
              onClick={clearError}
              className="mt-4 px-6 py-2 bg-red-500 hover:bg-red-600 text-white font-semibold rounded-lg transition-colors"
            >
              OK
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
