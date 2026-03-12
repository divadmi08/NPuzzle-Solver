import { usePuzzleStore } from '../store/usePuzzleStore';
import { INITIAL_GRID } from '../constants/puzzle';
import { gridToString } from '../utils/puzzle';

export default function Footer() {
  const allStates = usePuzzleStore(s => s.allStates);
  const totalSteps = usePuzzleStore(s => s.totalSteps);

  const initialStr = gridToString(INITIAL_GRID);
  const finalStr = gridToString(allStates[totalSteps]);

  return (
    <div className="text-center text-gray-500 text-[10px] sm:text-xs pb-4 font-mono">
      <span className="text-gray-600">[{initialStr}]</span>
      <span className="mx-2">→</span>
      <span className="text-green-600">[{finalStr}]</span>
    </div>
  );
}
