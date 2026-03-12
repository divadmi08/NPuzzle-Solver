import { useStep, useTotalSteps } from '@/features/puzzle/store/puzzleSelectors';

export default function ProgressBar() {
  const step = useStep();
  const totalSteps = useTotalSteps();
  const percentage = totalSteps > 0 ? (step / totalSteps) * 100 : 0;

  return (
    <div className="w-full max-w-md">
      <div className="h-2 bg-gray-700 rounded-full overflow-hidden">
        <div
          className="h-full bg-gradient-to-r from-blue-500 via-purple-500 to-pink-500 transition-all duration-300 rounded-full"
          style={{ width: `${percentage}%` }}
        />
      </div>
    </div>
  );
}
