import { usePuzzleStore } from '../store/usePuzzleStore';
import { DIRECTION_LABELS } from '../constants/puzzle';

export default function MoveInfo() {
  const step = usePuzzleStore(s => s.step);
  const totalSteps = usePuzzleStore(s => s.totalSteps);
  const currentMove = usePuzzleStore(s => s.currentMove);

  return (
    <div className="flex flex-wrap items-center justify-center gap-2 sm:gap-3 bg-gray-800/80 px-3 sm:px-5 py-2 sm:py-3 rounded-xl border border-gray-700/60">
      <span className="text-gray-400 text-xs sm:text-sm font-mono">
        Passo {step}/{totalSteps}
      </span>
      {currentMove && (
        <>
          <span className="text-gray-600 hidden sm:inline">|</span>
          <span className="text-xl sm:text-2xl">{DIRECTION_LABELS[currentMove].icon}</span>
          <span className="font-semibold text-yellow-300 text-sm sm:text-base">{currentMove}</span>
          <span className="text-gray-500 text-xs sm:text-sm">({DIRECTION_LABELS[currentMove].en})</span>
        </>
      )}
      {step === 0 && (
        <>
          <span className="text-gray-600 hidden sm:inline">|</span>
          <span className="text-gray-300 text-xs sm:text-sm">Stato Iniziale</span>
        </>
      )}
    </div>
  );
}
