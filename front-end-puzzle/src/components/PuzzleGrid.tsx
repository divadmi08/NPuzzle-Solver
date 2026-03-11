import { usePuzzleStore } from '../store/usePuzzleStore';
import { GRID_SIZE } from '../constants/puzzle';
import { getMovedTile } from '../utils/puzzle';
import Tile from './Tile';

export default function PuzzleGrid() {
  const currentGrid = usePuzzleStore(s => s.currentGrid);
  const prevGrid = usePuzzleStore(s => s.prevGrid);
  const isSolved = usePuzzleStore(s => s.isSolved);
  const totalSteps = usePuzzleStore(s => s.totalSteps);

  const movedTile = getMovedTile(currentGrid, prevGrid);

  return (
    <div className="relative flex justify-center w-full">
      <div
        className="grid gap-1.5 sm:gap-2 p-3 sm:p-4 bg-gray-800/60 rounded-xl sm:rounded-2xl backdrop-blur-sm border border-gray-700/50 shadow-2xl w-full max-w-[280px] sm:max-w-[360px] md:max-w-[440px]"
        style={{ gridTemplateColumns: `repeat(${GRID_SIZE}, 1fr)` }}
      >
        {currentGrid.flat().map((val, i) => (
          <Tile key={i} value={val} isMoving={val === movedTile} />
        ))}
      </div>

      {isSolved && (
        <div className="absolute inset-0 flex items-center justify-center">
          <div className="bg-black/50 rounded-xl sm:rounded-2xl backdrop-blur-[3px] p-6 sm:p-8 text-center">
            <div className="text-4xl sm:text-5xl mb-2 animate-bounce">🎉</div>
            <div className="text-lg sm:text-xl font-bold text-green-400">Risolto!</div>
            <div className="text-xs sm:text-sm text-gray-400 mt-1">in {totalSteps} mosse</div>
          </div>
        </div>
      )}
    </div>
  );
}
