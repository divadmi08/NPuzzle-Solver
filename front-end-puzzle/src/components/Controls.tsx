import { usePuzzleStore } from '../store/usePuzzleStore';

export default function Controls() {
  const step = usePuzzleStore(s => s.step);
  const totalSteps = usePuzzleStore(s => s.totalSteps);
  const isPlaying = usePuzzleStore(s => s.isPlaying);
  const reset = usePuzzleStore(s => s.reset);
  const goPrev = usePuzzleStore(s => s.goPrev);
  const goNext = usePuzzleStore(s => s.goNext);
  const togglePlay = usePuzzleStore(s => s.togglePlay);
  const pause = usePuzzleStore(s => s.pause);

  const handlePrev = () => { pause(); goPrev(); };
  const handleNext = () => { pause(); goNext(); };

  return (
    <div className="flex flex-wrap items-center justify-center gap-2 sm:gap-3">
      <button
        onClick={reset}
        className="px-3 sm:px-4 py-2 bg-gray-700 hover:bg-gray-600 rounded-lg text-xs sm:text-sm font-medium transition-colors border border-gray-600 active:scale-95"
      >
        ⟲ Reset
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
