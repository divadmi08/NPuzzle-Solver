'use client';

import { useSelector } from "react-redux";
import type { RootState } from "../lib/store";

export default function Table() {
  const size = useSelector((state: RootState) => state.tableSize.size);

  return (
    <div
      className="grid gap-2"
      style={{ gridTemplateColumns: `repeat(${size}, minmax(0, 1fr))` }}
    >
      {Array.from({ length: size * size }).map((_, i) => (
        <div
          key={i}
          className="
            w-16 h-16 flex items-center justify-center
            bg-slate-700 hover:bg-cyan-700 cursor-pointer
            rounded-lg border border-slate-500
            text-white font-bold text-xl
            transition-colors
          "
        >
          {i + 1}
        </div>
      ))}
    </div>
  );
}
