import { GRID_SIZE, MOVES } from '../constants/puzzle';

export default function Header() {
  const puzzleName = GRID_SIZE === 4 ? '15' : GRID_SIZE === 3 ? '8' : `${GRID_SIZE * GRID_SIZE - 1}`;

  return (
    <div className="text-center">
      <h1 className="text-3xl sm:text-4xl md:text-5xl font-extrabold bg-gradient-to-r from-blue-400 via-purple-400 to-pink-400 bg-clip-text text-transparent">
        {puzzleName}-Puzzle Solver
      </h1>
      <p className="text-gray-400 mt-1 sm:mt-2 text-xs sm:text-sm md:text-base">
        {GRID_SIZE}×{GRID_SIZE} puzzle · {MOVES.length} mosse per risolvere
      </p>
    </div>
  );
}
