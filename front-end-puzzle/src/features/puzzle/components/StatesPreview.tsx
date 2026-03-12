import { memo } from 'react';
import { useAllStates, useTotalSteps, useGridSize, useInitialGrid } from '@/features/puzzle/store/puzzleSelectors';

const MiniGrid = memo(function MiniGrid({
  grid,
  gridSize,
  label,
  labelColor,
  borderColor,
}: {
  grid: number[][];
  gridSize: number;
  label: string;
  labelColor: string;
  borderColor: string;
}) {
  return (
    <div className={`bg-gray-800/40 rounded-xl border ${borderColor} p-3 sm:p-4`}>
      <h3 className={`text-xs font-semibold ${labelColor} uppercase tracking-wider mb-2`}>
        {label}
      </h3>
      <div className="grid gap-1" style={{ gridTemplateColumns: `repeat(${gridSize}, 1fr)` }}>
        {grid.flat().map((val, i) => (
          <div
            key={i}
            className={`
              aspect-square rounded flex items-center justify-center text-xs sm:text-sm font-bold
              ${val === 0
                ? 'bg-gray-700/30 text-gray-600'
                : label.includes('✓')
                ? 'bg-green-900/40 text-green-400'
                : 'bg-gray-700/60 text-gray-300'
              }
            `}
          >
            {val === 0 ? '·' : val}
          </div>
        ))}
      </div>
    </div>
  );
});

const StatesPreview = memo(function StatesPreview() {
  const allStates = useAllStates();
  const totalSteps = useTotalSteps();
  const gridSize = useGridSize();
  const initialGrid = useInitialGrid();
  const finalGrid = allStates[totalSteps];

  return (
    <div className="w-full grid grid-cols-1 sm:grid-cols-2 gap-3 sm:gap-4">
      <MiniGrid
        grid={initialGrid}
        gridSize={gridSize}
        label="Stato Iniziale"
        labelColor="text-gray-500"
        borderColor="border-gray-700/40"
      />
      <MiniGrid
        grid={finalGrid}
        gridSize={gridSize}
        label="Stato Finale ✓"
        labelColor="text-green-500"
        borderColor="border-green-700/30"
      />
    </div>
  );
});

export default StatesPreview;
