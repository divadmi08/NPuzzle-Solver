import { memo } from 'react';
import { useCurrentGrid, usePrevGrid, useIsSolved, useTotalSteps, useGameMode, useManualGrid, usePlayMove, useGridSize } from '@/features/puzzle/store/puzzleSelectors';
import { DIRECTION_DELTAS } from '@/features/puzzle/constants/puzzle';
import { getMovedTile } from '@/features/puzzle/utils/puzzle';
import Tile from './Tile';

const PuzzleGrid = memo(function PuzzleGrid() {
  const gameMode = useGameMode();
  const currentGrid = useCurrentGrid();
  const manualGrid = useManualGrid();
  const gridSize = useGridSize();
  const prevGrid = usePrevGrid();
  const isSolved = useIsSolved();
  const totalSteps = useTotalSteps();
  const playMove = usePlayMove();

  // Scegli quale grid mostrare
  const displayGrid = gameMode === 'play' ? manualGrid : currentGrid;
  const movedTile = gameMode === 'replay' ? getMovedTile(currentGrid, prevGrid) : -1;
  const solvedTitle = gameMode === 'play' ? 'Vittoria!' : 'Risolto!';

  const handleTileClick = (index: number) => {
    if (gameMode !== 'play') return;

    const row = Math.floor(index / gridSize);
    const col = index % gridSize;

    // Trova lo zero (casella vuota)
    let zeroRow = 0, zeroCol = 0;
    for (let r = 0; r < gridSize; r++) {
      for (let c = 0; c < gridSize; c++) {
        if (manualGrid[r][c] === 0) {
          zeroRow = r;
          zeroCol = c;
        }
      }
    }

    // Verifica se il tile è adiacente allo zero
    const directions = ['SINISTRA', 'DESTRA', 'SOPRA', 'SOTTO'] as const;
    for (const dir of directions) {
      const [dr, dc] = DIRECTION_DELTAS[dir];
      if (zeroRow + dr === row && zeroCol + dc === col) {
        playMove(dir);
        return;
      }
    }
  };

  const gridMaxClass =
    gridSize === 3
      ? 'max-w-[460px] sm:max-w-[580px] lg:max-w-[700px]'
      : 'max-w-[380px] sm:max-w-[500px] lg:max-w-[620px]';

  return (
    <div className="relative flex justify-center w-full">
      <div
        className={`grid gap-1.5 sm:gap-2 p-3 sm:p-4 bg-gray-800/60 rounded-xl sm:rounded-2xl backdrop-blur-sm border border-gray-700/50 shadow-2xl w-full ${gridMaxClass}`}
        style={{ gridTemplateColumns: `repeat(${gridSize}, 1fr)` }}
      >
        {displayGrid.flat().map((val, i) => (
          <div
            key={i}
            onClick={() => handleTileClick(i)}
            className={gameMode === 'play' ? 'cursor-pointer' : ''}
          >
            <Tile value={val} isMoving={val === movedTile} />
          </div>
        ))}
      </div>

      {isSolved && (
        <div className="absolute inset-0 flex items-center justify-center">
          <div className="bg-black/50 rounded-xl sm:rounded-2xl backdrop-blur-[3px] p-6 sm:p-8 text-center">
            <div className="text-4xl sm:text-5xl mb-2 animate-bounce">🎉</div>
            <div className="text-lg sm:text-xl font-bold text-green-400">{solvedTitle}</div>
            {gameMode === 'replay' ? (
              <div className="text-xs sm:text-sm text-gray-400 mt-1">in {totalSteps} mosse</div>
            ) : (
              <div className="text-xs sm:text-sm text-gray-400 mt-1">Ordine corretto con spazio in basso a destra</div>
            )}
          </div>
        </div>
      )}
    </div>
  );
});

export default PuzzleGrid;
