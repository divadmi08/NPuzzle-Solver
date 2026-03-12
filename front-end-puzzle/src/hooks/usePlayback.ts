"use client";

import { useEffect, useRef } from 'react';
import { usePuzzleStore } from '../store/usePuzzleStore';

/**
 * Hook che gestisce l'autoplay del puzzle.
 * Crea un interval che avanza di un passo alla velocità impostata.
 */
export function usePlayback() {
  const intervalRef = useRef<ReturnType<typeof setInterval> | null>(null);
  const isPlaying = usePuzzleStore((s: { isPlaying: any; }) => s.isPlaying);
  const speed = usePuzzleStore((s: { speed: any; }) => s.speed);
  const tick = usePuzzleStore((s: { tick: any; }) => s.tick);
  const pause = usePuzzleStore((s: { pause: any; }) => s.pause);

  useEffect(() => {
    if (isPlaying) {
      intervalRef.current = setInterval(() => {
        const hasMore = tick();
        if (!hasMore) {
          pause();
        }
      }, speed);
    }

    return () => {
      if (intervalRef.current) {
        clearInterval(intervalRef.current);
        intervalRef.current = null;
      }
    };
  }, [isPlaying, speed, tick, pause]);
}
