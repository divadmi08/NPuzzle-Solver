import { configureStore } from "@reduxjs/toolkit";
import timerReducer from "./features/timerSlice/timerSlice";

export const store = configureStore({
  reducer: {
    timer: timerReducer,
  },
});