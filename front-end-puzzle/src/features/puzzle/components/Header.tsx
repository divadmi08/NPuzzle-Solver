import { memo } from 'react';
import { useGridSize, useSolutionMoves } from '@/features/puzzle/store/puzzleSelectors';

const Header = memo(function Header() {
  const gridSize = useGridSize();
  const moves = useSolutionMoves();
  const puzzleName = gridSize === 4 ? '15' : '8';

  return (
    <div className="text-center">
      <h1 className="text-3xl sm:text-4xl md:text-5xl font-extrabold bg-gradient-to-r from-blue-400 via-purple-400 to-pink-400 bg-clip-text text-transparent">
        {puzzleName}-Puzzle Solver
      </h1>
      <p className="text-gray-400 mt-1 sm:mt-2 text-xs sm:text-sm md:text-base">
        {gridSize}×{gridSize} puzzle · {moves.length} mosse per risolvere
      </p>
    </div>
  );
});

export default Header;
