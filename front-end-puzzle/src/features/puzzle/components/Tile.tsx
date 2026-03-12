import { memo } from 'react';
import { TILE_COLORS_BY_MODE } from '@/features/puzzle/constants/puzzle';
import { useColorPaletteMode } from '@/features/puzzle/store/puzzleSelectors';

interface TileProps {
  value: number;
  isMoving: boolean;
}

const Tile = memo(function Tile({ value, isMoving }: TileProps) {
  const colorPaletteMode = useColorPaletteMode();

  if (value === 0) {
    return (
      <div className="aspect-square rounded-lg sm:rounded-xl bg-gray-800/40 border-2 border-dashed border-gray-600/50" />
    );
  }

  const palette = TILE_COLORS_BY_MODE[colorPaletteMode];

  return (
    <div
      className={`
        aspect-square rounded-lg sm:rounded-xl
        bg-gradient-to-br ${palette[value] || 'from-gray-500 to-gray-600'}
        shadow-lg flex items-center justify-center text-white font-bold
        text-lg sm:text-2xl md:text-3xl
        border border-white/20 transition-all duration-300
        ${isMoving ? 'scale-105 sm:scale-110 shadow-2xl ring-2 ring-white/50' : 'scale-100'}
      `}
    >
      {value}
    </div>
  );
});

export default Tile;
