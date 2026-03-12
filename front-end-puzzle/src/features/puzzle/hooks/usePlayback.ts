"use client";

import { useEffect, useRef } from 'react';
import { useIsPlaying, useSpeed, useTick, usePause } from '@/features/puzzle/store/puzzleSelectors';

/**
 * Hook che gestisce l'autoplay del puzzle.
 * Crea un interval che avanza di un passo alla velocità impostata.
 * Fixed: Ottimizzato per evitare memory leaks con dipendenze corrette.
 */
export function usePlayback() {
  const intervalRef = useRef<ReturnType<typeof setInterval> | null>(null);
  const isPlaying = useIsPlaying();
  const speed = useSpeed();
  const tick = useTick();
  const pause = usePause();

  useEffect(() => {
    if (!isPlaying) {
      if (intervalRef.current) {
        clearInterval(intervalRef.current);
        intervalRef.current = null;
      }
      return;
    }

    intervalRef.current = setInterval(() => {
      const hasMore = tick();
      if (!hasMore) {
        pause();
      }
    }, speed);

    return () => {
      if (intervalRef.current) {
        clearInterval(intervalRef.current);
        intervalRef.current = null;
      }
    };
  }, [isPlaying, speed, tick, pause]);
}
