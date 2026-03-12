"use client";

import { useEffect, useRef } from 'react';
import { useStep, useJumpToStep, useSolutionMoves } from '@/features/puzzle/store/puzzleSelectors';
import { DIRECTION_LABELS } from '@/features/puzzle/constants/puzzle';

export default function MoveList() {
  const step = useStep();
  const jumpToStep = useJumpToStep();
  const moves = useSolutionMoves();
  const listRef = useRef<HTMLDivElement>(null);

  // Auto-scroll alla mossa attiva
  useEffect(() => {
    if (listRef.current && step > 0) {
      const activeBtn = listRef.current.querySelector(`[data-step="${step}"]`);
      if (activeBtn) {
        activeBtn.scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'center' });
      }
    }
  }, [step]);

  return (
    <div className="w-full bg-gray-800/50 rounded-xl border border-gray-700/50 p-3 sm:p-4">
      <h2 className="text-xs sm:text-sm font-semibold text-gray-400 mb-2 sm:mb-3 uppercase tracking-wider">
        Sequenza Mosse ({moves.length})
      </h2>
      <div ref={listRef} className="max-h-44 overflow-y-auto pr-1 flex flex-wrap gap-1 sm:gap-1.5">
        {moves.map((move, i) => {
          const moveIndex = i + 1;
          const isActive = moveIndex === step;
          const isPast = moveIndex < step;

          return (
            <button
              key={i}
              data-step={moveIndex}
              onClick={() => jumpToStep(moveIndex)}
              title={`#${moveIndex}: ${move} (${DIRECTION_LABELS[move].en})`}
              className={`
                px-1.5 sm:px-2 py-0.5 sm:py-1 rounded text-[10px] sm:text-xs font-mono
                transition-all border cursor-pointer
                ${isActive
                  ? 'bg-yellow-500/30 border-yellow-500 text-yellow-300 scale-110 shadow-lg shadow-yellow-500/20 z-10'
                  : isPast
                  ? 'bg-green-900/30 border-green-700/50 text-green-400/80'
                  : 'bg-gray-700/40 border-gray-600/40 text-gray-400 hover:bg-gray-600/50'
                }
              `}
            >
              {DIRECTION_LABELS[move].icon}
            </button>
          );
        })}
      </div>
    </div>
  );
}
