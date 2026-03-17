"use client";

import Link from "next/link";
import { useEffect, useMemo, useState, type CSSProperties } from "react";

const GRID_SIDE = 4;
const CELL_COUNT = GRID_SIDE * GRID_SIDE;
const EMPTY_TILE = 0;

const TILE_COLORS: ReadonlyArray<[string, string]> = [
  ["rgba(59,130,246,0.3)", "rgba(37,99,235,0.2)"],
  ["rgba(16,185,129,0.3)", "rgba(5,150,105,0.2)"],
  ["rgba(245,158,11,0.3)", "rgba(217,119,6,0.2)"],
  ["rgba(168,85,247,0.3)", "rgba(147,51,234,0.2)"],
  ["rgba(244,63,94,0.3)", "rgba(225,29,72,0.2)"],
  ["rgba(6,182,212,0.3)", "rgba(8,145,178,0.2)"],
  ["rgba(249,115,22,0.3)", "rgba(234,88,12,0.2)"],
  ["rgba(99,102,241,0.3)", "rgba(79,70,229,0.2)"],
];

function getNeighbors(index: number): number[] {
  const row = Math.floor(index / GRID_SIDE);
  const col = index % GRID_SIDE;
  const neighbors: number[] = [];

  if (row > 0) neighbors.push(index - GRID_SIDE);
  if (row < GRID_SIDE - 1) neighbors.push(index + GRID_SIDE);
  if (col > 0) neighbors.push(index - 1);
  if (col < GRID_SIDE - 1) neighbors.push(index + 1);

  return neighbors;
}

function moveOnce(board: number[]): number[] {
  const emptyIndex = board.indexOf(EMPTY_TILE);
  const neighbors = getNeighbors(emptyIndex);
  const randomNeighbor = neighbors[Math.floor(Math.random() * neighbors.length)];
  const next = [...board];
  [next[emptyIndex], next[randomNeighbor]] = [next[randomNeighbor], next[emptyIndex]];
  return next;
}

function getTileBackground(tile: number): string {
  const [from, to] = TILE_COLORS[(tile - 1) % TILE_COLORS.length];
  return `linear-gradient(145deg, ${from}, ${to})`;
}

export default function Home() {
  const [animatedBoard, setAnimatedBoard] = useState<number[]>(
    Array.from({ length: CELL_COUNT }, (_, i) => (i === CELL_COUNT - 1 ? EMPTY_TILE : i + 1)),
  );

  useEffect(() => {
    const interval = setInterval(() => {
      setAnimatedBoard(previous => moveOnce(previous));
    }, 1900);

    return () => clearInterval(interval);
  }, []);

  const tilePositions = useMemo(() => {
    const positions = new Map<number, number>();
    animatedBoard.forEach((tile, index) => {
      positions.set(tile, index);
    });
    return positions;
  }, [animatedBoard]);

  const renderedTiles = Array.from({ length: CELL_COUNT }, (_, i) => (i === CELL_COUNT - 1 ? EMPTY_TILE : i + 1));

  return (
    <main className="relative h-full w-full overflow-y-auto text-white">
      <div className="pointer-events-none absolute inset-0 flex items-center justify-center">
        <div className="home-board-bg" aria-hidden="true">
          <div className="home-board-canvas">
            {renderedTiles.map((tile) => {
              const isEmpty = tile === EMPTY_TILE;
              const cellIndex = tilePositions.get(tile) ?? 0;
              const row = Math.floor(cellIndex / GRID_SIDE);
              const col = cellIndex % GRID_SIDE;

              return (
                <div
                  key={tile}
                  className={`home-board-tile home-board-swap ${isEmpty ? 'home-board-empty' : ''}`}
                  style={
                    {
                      left: `calc(${col} * (var(--tile-size) + var(--tile-gap)))`,
                      top: `calc(${row} * (var(--tile-size) + var(--tile-gap)))`,
                      '--tile-bg': isEmpty ? 'rgba(15, 23, 42, 0.14)' : getTileBackground(tile),
                      animationDelay: `${(tile % 8) * 0.55}s`,
                    } as CSSProperties
                  }
                />
              );
            })}
          </div>
        </div>
      </div>

      <div className="pointer-events-none absolute inset-0 opacity-70">
        <div className="absolute -top-24 left-1/2 h-72 w-72 -translate-x-1/2 rounded-full bg-cyan-500/20 blur-3xl" />
        <div className="absolute right-0 top-1/3 h-64 w-64 rounded-full bg-pink-500/10 blur-3xl" />
        <div className="absolute bottom-0 left-0 h-64 w-64 rounded-full bg-emerald-500/10 blur-3xl" />
      </div>

      <div className="relative mx-auto flex h-full w-full max-w-6xl flex-col justify-center px-4 py-8 sm:px-8">
        <div className="rounded-3xl border border-slate-700/80 bg-slate-950/70 p-6 shadow-2xl shadow-cyan-950/30 sm:p-8 md:p-10">
          <p className="text-xs font-semibold uppercase tracking-[0.28em] text-cyan-300">NPuzzle Experience</p>
          <h1 className="mt-3 text-4xl font-black leading-tight sm:text-5xl md:text-6xl">
            <span className="bg-linear-to-r from-cyan-300 via-blue-300 to-violet-300 bg-clip-text text-transparent">15-Puzzle Solver</span>
          </h1>
          <p className="mt-4 max-w-3xl text-sm text-slate-300 sm:text-base">
            Gioca manualmente, carica una tabella personalizzata o testa la risoluzione automatica con replay passo-passo.
            Interfaccia ottimizzata per desktop e mobile.
          </p>

          <div className="mt-7 grid grid-cols-1 gap-3 sm:grid-cols-2 lg:grid-cols-3">
            {[
              {
                href: "/game",
                title: "Avvia Gioco",
                description: "Partita rapida con tabella generata automaticamente.",
                className: "from-cyan-600/85 to-blue-600/85",
              },
              {
                href: "/custom",
                title: "Tabella Manuale",
                description: "Inserisci uno stato iniziale e prova la tua configurazione.",
                className: "from-emerald-600/85 to-teal-600/85",
              },
              {
                href: "/settings",
                title: "Impostazioni",
                description: "Tema, palette e preferenze visive.",
                className: "from-violet-600/85 to-fuchsia-600/85",
              },
            ].map((item) => (
              <Link
                key={item.href}
                href={item.href}
                className={`group rounded-2xl border border-white/10 bg-linear-to-br p-4 text-left transition-all duration-300 hover:-translate-y-0.5 hover:border-cyan-300/60 hover:shadow-lg hover:shadow-cyan-900/40 ${item.className}`}
              >
                <h2 className="text-lg font-extrabold text-white sm:text-xl">{item.title}</h2>
                <p className="mt-2 text-xs leading-relaxed text-white/85 sm:text-sm">{item.description}</p>
              </Link>
            ))}
          </div>

          <div className="mt-7 flex flex-wrap gap-2 text-[11px] sm:text-xs">
            {[
              '3x3 e 4x4',
              'Replay animato',
              'Mosse minime dal backend',
              'Configurazione custom',
            ].map((badge) => (
              <span key={badge} className="rounded-full border border-slate-600 bg-slate-900/70 px-3 py-1 text-slate-300">
                {badge}
              </span>
            ))}
          </div>
        </div>
      </div>
    </main>
  );
}