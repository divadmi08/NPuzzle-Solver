import { usePuzzleStore } from '../store/usePuzzleStore';
import { MIN_SPEED, MAX_SPEED, SPEED_STEP } from '../constants/puzzle';

export default function SpeedControl() {
  const speed = usePuzzleStore(s => s.speed);
  const setSpeed = usePuzzleStore(s => s.setSpeed);

  const invertedMax = MIN_SPEED + MAX_SPEED;

  return (
    <div className="flex items-center gap-2 sm:gap-3 bg-gray-800/60 rounded-lg px-3 sm:px-4 py-2 border border-gray-700/40">
      <span className="text-gray-400 text-xs sm:text-sm">Velocità:</span>
      <span className="text-gray-500 text-xs">🐢</span>
      <input
        type="range"
        min={MIN_SPEED}
        max={MAX_SPEED}
        step={SPEED_STEP}
        value={invertedMax - speed}
        onChange={e => setSpeed(invertedMax - Number(e.target.value))}
        className="w-24 sm:w-32 accent-purple-500"
      />
      <span className="text-gray-500 text-xs">🐇</span>
      <span className="text-gray-300 text-xs font-mono w-14 text-right">{speed}ms</span>
    </div>
  );
}
