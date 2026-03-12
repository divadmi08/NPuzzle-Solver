import { useStep, useTotalSteps, useIsPlaying, useGoPrev, useGoNext, useTogglePlay, usePause, useGameMode, useRestartGame } from '@/features/puzzle/store/puzzleSelectors';

export default function Controls() {
  const gameMode = useGameMode();
  const step = useStep();
  const totalSteps = useTotalSteps();
  const isPlaying = useIsPlaying();
  const goPrev = useGoPrev();
  const goNext = useGoNext();
  const togglePlay = useTogglePlay();
  const pause = usePause();
  const restartGame = useRestartGame();

  const handlePrev = () => { pause(); goPrev(); };
  const handleNext = () => { pause(); goNext(); };

  if (gameMode === 'play') {
    return null;
  }

  // gameMode === 'replay'
  return (
    <div className="flex flex-wrap items-center justify-center gap-2 sm:gap-3">
      <button
        onClick={restartGame}
        className="px-3 sm:px-4 py-2 bg-gray-700 hover:bg-gray-600 rounded-lg text-xs sm:text-sm font-medium transition-colors border border-gray-600 active:scale-95"
      >
        ⟲ Riprova
      </button>
      <button
        onClick={handlePrev}
        disabled={step === 0}
        className="px-3 sm:px-4 py-2 bg-gray-700 hover:bg-gray-600 disabled:opacity-40 disabled:cursor-not-allowed rounded-lg text-xs sm:text-sm font-medium transition-colors border border-gray-600 active:scale-95"
      >
        ◀ Indietro
      </button>
      <button
        onClick={togglePlay}
        className={`px-4 sm:px-6 py-2 rounded-lg text-xs sm:text-sm font-bold transition-colors border active:scale-95 ${
          isPlaying
            ? 'bg-red-600 hover:bg-red-500 border-red-500'
            : 'bg-green-600 hover:bg-green-500 border-green-500'
        }`}
      >
        {isPlaying ? '⏸ Pausa' : '▶ Avvia'}
      </button>
      <button
        onClick={handleNext}
        disabled={step === totalSteps}
        className="px-3 sm:px-4 py-2 bg-gray-700 hover:bg-gray-600 disabled:opacity-40 disabled:cursor-not-allowed rounded-lg text-xs sm:text-sm font-medium transition-colors border border-gray-600 active:scale-95"
      >
        Avanti ▶
      </button>
    </div>
  );
}
