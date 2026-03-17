import { memo, useEffect, useState } from 'react';
import {
  useCurrentGrid,
  useElapsedSeconds,
  useSetGeneratedPuzzle,
  useSetGridSize,
  usePrevGrid,
  useIsSolved,
  useTotalSteps,
  useGameMode,
  useManualGrid,
  usePlayMove,
  useGridSize,
  useMinimumMoves,
  usePlayerMoves,
} from '@/features/puzzle/store/puzzleSelectors';
import { postGeneratePuzzle } from '@/features/puzzle/api/solverApi';
import { DIRECTION_DELTAS } from '@/features/puzzle/constants/puzzle';
import { getMovedTile } from '@/features/puzzle/utils/puzzle';
import Tile from './Tile';

function formatTime(totalSeconds: number): string {
  const mins = Math.floor(totalSeconds / 60);
  const secs = totalSeconds % 60;
  return `${String(mins).padStart(2, '0')}:${String(secs).padStart(2, '0')}`;
}

const PuzzleGrid = memo(function PuzzleGrid() {
  const gameMode = useGameMode();
  const currentGrid = useCurrentGrid();
  const manualGrid = useManualGrid();
  const gridSize = useGridSize();
  const prevGrid = usePrevGrid();
  const isSolved = useIsSolved();
  const totalSteps = useTotalSteps();
  const playerMoves = usePlayerMoves();
  const minimumMoves = useMinimumMoves();
  const elapsedSeconds = useElapsedSeconds();
  const setGeneratedPuzzle = useSetGeneratedPuzzle();
  const setGridSize = useSetGridSize();
  const playMove = usePlayMove();
  const [dismissSolvedPopup, setDismissSolvedPopup] = useState(false);
  const [loadingNewPuzzle, setLoadingNewPuzzle] = useState(false);

  // Scegli quale grid mostrare
  const displayGrid = gameMode === 'play' ? manualGrid : currentGrid;
  const movedTile = gameMode === 'replay' ? getMovedTile(currentGrid, prevGrid) : -1;
  const solvedTitle = gameMode === 'play' ? 'Vittoria!' : 'Risolto!';
  const shouldShowSolvedPopup = isSolved && !dismissSolvedPopup;

  useEffect(() => {
    if (!isSolved) {
      setDismissSolvedPopup(false);
      setLoadingNewPuzzle(false);
    }
  }, [isSolved]);

  const handlePlayAgain = async () => {
    if (loadingNewPuzzle) return;

    setDismissSolvedPopup(true);
    setLoadingNewPuzzle(true);

    try {
      const generatedPuzzle = await postGeneratePuzzle(gridSize);
      await setGeneratedPuzzle(gridSize, generatedPuzzle.initialGrid, generatedPuzzle.moves);
    } catch {
      setGridSize(gridSize);
    } finally {
      setLoadingNewPuzzle(false);
    }
  };

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

      {shouldShowSolvedPopup && (
        <div className="absolute inset-0 flex items-center justify-center">
          <div className="bg-black/55 rounded-xl sm:rounded-2xl backdrop-blur-[3px] p-6 sm:p-8 text-center border border-emerald-400/30 min-w-65">
            <div className="text-lg sm:text-xl font-bold text-green-400">{solvedTitle}</div>
            <div className="text-xs sm:text-sm text-cyan-300 mt-1">
              Tempo risoluzione puzzle: {formatTime(elapsedSeconds)}
            </div>
            {gameMode === 'replay' ? (
              <div className="text-xs sm:text-sm text-gray-400 mt-1">in {totalSteps} mosse</div>
            ) : (
              <div className="mt-1">
                <div className="text-xs sm:text-sm text-gray-300">
                  Hai risolto in <span className="font-semibold text-emerald-300">{playerMoves}</span> mosse
                </div>
                <div className="text-xs sm:text-sm text-gray-400">
                  {minimumMoves === null
                    ? 'Mosse minime: in attesa del backend'
                    : `Minimo ottimale: ${minimumMoves} mosse`}
                </div>
              </div>
            )}
            <button
              type="button"
              onClick={() => {
                void handlePlayAgain();
              }}
              disabled={loadingNewPuzzle}
              className="mt-4 rounded-lg border border-cyan-400/60 bg-cyan-700 px-4 py-2 text-sm font-semibold text-white transition-colors hover:bg-cyan-600 disabled:opacity-60"
            >
              {loadingNewPuzzle ? 'Caricamento...' : 'Gioca ancora'}
            </button>
          </div>
        </div>
      )}
    </div>
  );
});

export default PuzzleGrid;
