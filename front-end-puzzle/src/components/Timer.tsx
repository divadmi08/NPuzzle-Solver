"use client";

import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { start, stop, reset, tick } from "../lib/features/timerSlice/timerSlice";

export default function Timer() {
  const dispatch = useDispatch();
  const time = useSelector((state) => state.timer.time);
  const running = useSelector((state) => state.timer.running);

  useEffect(() => {
    let interval: number;

    if (running) {
      interval = setInterval(() => {
        dispatch(tick());
      }, 1000); // 1 secondo
    }

    return () => clearInterval(interval);
  }, [running, dispatch]);

  return (
    <div style={{ fontSize: "40px", textAlign: "center" }}>
      <h1>{time} s</h1>

      {!running && (
        <button onClick={() => dispatch(start())}>Start</button>
      )}

      {running && (
        <button onClick={() => dispatch(stop())}>Stop</button>
      )}

      <button onClick={() => dispatch(reset())}>Reset</button>
    </div>
  );
}