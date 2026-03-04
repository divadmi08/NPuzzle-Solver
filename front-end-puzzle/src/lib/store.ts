import { configureStore } from "@reduxjs/toolkit";
import timerReducer from "./features/timerSlice/timerSlice";
import tableSizeReducer from "./features/tableSizeSlice";

export const store = configureStore({
  reducer: {
    timer: timerReducer,
    tableSize: tableSizeReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
