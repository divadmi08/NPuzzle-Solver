import { memo } from 'react';
import { useAllStates, useTotalSteps, useInitialGrid } from '@/features/puzzle/store/puzzleSelectors';
import { gridToString } from '@/features/puzzle/utils/puzzle';

const Footer = memo(function Footer() {
  const allStates = useAllStates();
  const totalSteps = useTotalSteps();
  const initialGrid = useInitialGrid();

  const initialStr = gridToString(initialGrid);
  const finalStr = gridToString(allStates[totalSteps]);

  return (
    <div className="text-center text-gray-500 text-[10px] sm:text-xs pb-4 font-mono">
      <span className="text-gray-600">[{initialStr}]</span>
      <span className="mx-2">→</span>
      <span className="text-green-600">[{finalStr}]</span>
    </div>
  );
});

export default Footer;
