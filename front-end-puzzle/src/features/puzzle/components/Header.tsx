import { memo } from 'react';
import { useGameMode, useGridSize, useMinimumMoves, useTotalSteps } from '@/features/puzzle/store/puzzleSelectors';

const Header = memo(function Header() {
  const gridSize = useGridSize();
  const minimumMoves = useMinimumMoves();
  const totalSteps = useTotalSteps();
  const gameMode = useGameMode();
  const puzzleName = gridSize === 4 ? '15' : '8';
  const visibleMinimumMoves = minimumMoves ?? (gameMode === 'replay' ? totalSteps : null);

  return (
    <div className="text-center">
      <h1 className="text-3xl sm:text-4xl md:text-5xl font-extrabold bg-linear-to-r from-blue-400 via-purple-400 to-pink-400 bg-clip-text text-transparent">
        {puzzleName}-Puzzle Solver
      </h1>
      <p className="text-gray-400 mt-1 sm:mt-2 text-xs sm:text-sm md:text-base">
        {gridSize}×{gridSize} puzzle · {visibleMinimumMoves === null ? 'mosse minime da backend...' : `minimo: ${visibleMinimumMoves} mosse`}
      </p>
    </div>
  );
});

export default Header;
