'use client';

import { useEffect, useRef, useState } from "react";

interface Props {
  moves: number;
  onQuit: () => void;
}

export default function ControlBar({ moves, onQuit }: Props) {
  const [time, setTime] = useState(0);
  const [isRunning, setIsRunning] = useState(true);
  const [finished, setFinished] = useState(false);

  const requestRef = useRef<number | null>(null);
  const startRef = useRef<number>(0);
  const audioRef = useRef<HTMLAudioElement | null>(null);

  // 🔊 preload sound
  useEffect(() => {
    audioRef.current = new Audio("/tick.mp3");
  }, []);

  // 💾 load time
  useEffect(() => {
    const saved = localStorage.getItem("arcade-time");
    if (saved) setTime(Number(saved));
  }, []);

  // 💾 save time
  useEffect(() => {
    localStorage.setItem("arcade-time", time.toString());
  }, [time]);

  // TIMER smooth
  useEffect(() => {
    if (!isRunning) return;

    startRef.current = performance.now() - time * 1000;

    const update = (now: number) => {
      const diff = (now - startRef.current) / 1000;
      setTime(diff);
      requestRef.current = requestAnimationFrame(update);
    };

    requestRef.current = requestAnimationFrame(update);

    return () => {
      if (requestRef.current) cancelAnimationFrame(requestRef.current);
    };
  }, [isRunning]);

  // 🔊 tick ogni secondo
  useEffect(() => {
    audioRef.current?.play().catch(() => {});
  }, [Math.floor(time)]);

  const handlePause = () => setIsRunning(false);
  const handleResume = () => setIsRunning(true);

  const handleFinish = () => {
    setIsRunning(false);
    setFinished(true);
    localStorage.removeItem("arcade-time");
  };

  const seconds = Math.floor(time);
  const minutes = Math.floor(seconds / 60);
  const remaining = seconds % 60;
  const formatted = `${minutes}:${remaining
    .toString()
    .padStart(2, "0")}`;

  const minuteFlash = remaining === 0;

  return (
    <>
      <nav className="fixed top-0 left-0 w-full h-20 bg-black border-b border-cyan-500 flex items-center justify-between px-8 z-50 shadow-[0_0_20px_#00ffff]">

        {/* LEFT CONTROLS */}
        <div className="flex gap-4">
          <button
            onClick={handlePause}
            className="px-4 py-2 bg-yellow-600 hover:bg-yellow-500 rounded-lg font-bold"
          >
            ⏸
          </button>

          <button
            onClick={handleFinish}
            className="px-4 py-2 bg-pink-600 hover:bg-pink-500 rounded-lg font-bold"
          >
            🏁
          </button>
        </div>

        {/* CENTER TIMER */}
        <div className="relative flex items-center justify-center">

          <div className="absolute w-40 h-40 rounded-full bg-cyan-500 blur-3xl opacity-20 animate-pulse"></div>

          <div
            className={`text-3xl font-extrabold tracking-widest 
            ${minuteFlash ? "text-pink-500" : "text-cyan-400"}
            animate-[pulse_1s_ease-in-out_infinite]`}
          >
            ⏱ {formatted}
          </div>
        </div>

        {/* RIGHT INFO */}
        <div className="flex items-center gap-6 text-lg font-bold text-cyan-400">
          <span>🧩 {moves}</span>

          <button
            onClick={onQuit}
            className="px-4 py-2 bg-red-600 hover:bg-red-500 rounded-lg"
          >
            ❌
          </button>
        </div>
      </nav>

      {/* PAUSE MODAL */}
      {!isRunning && !finished && (
        <div className="fixed inset-0 bg-black/80 backdrop-blur-md flex items-center justify-center z-40">
          <div className="bg-zinc-900 p-10 rounded-2xl border border-cyan-500 shadow-[0_0_40px_#00ffff] text-center space-y-6">
            <h2 className="text-4xl text-cyan-400 font-bold animate-pulse">
              PAUSA
            </h2>

            <button
              onClick={handleResume}
              className="px-8 py-3 bg-cyan-500 hover:bg-cyan-400 rounded-lg font-bold"
            >
              RIPRENDI
            </button>
          </div>
        </div>
      )}

      {/* STATS MODAL */}
      {finished && (
        <div className="fixed inset-0 bg-black flex items-center justify-center z-40">
          <div className="bg-zinc-900 p-12 rounded-2xl border border-pink-500 shadow-[0_0_50px_#ff00ff] text-center space-y-6">
            <h2 className="text-5xl text-pink-500 font-extrabold">
              🎉 VITTORIA
            </h2>

            <p className="text-2xl text-white">
              Tempo: {formatted}
            </p>

            <p className="text-2xl text-white">
              Mosse: {moves}
            </p>

            <button
              onClick={onQuit}
              className="px-6 py-3 bg-pink-600 hover:bg-pink-500 rounded-lg font-bold"
            >
              ESCI
            </button>
          </div>
        </div>
      )}
    </>
  );
}