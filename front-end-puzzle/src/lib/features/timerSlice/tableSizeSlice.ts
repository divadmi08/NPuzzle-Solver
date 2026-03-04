import { createSlice, PayloadAction } from "@reduxjs/toolkit";

const tableSizeSlice = createSlice({
  name: "tableSize",
  initialState: {
    size: 3, // default 3x3
  },
  reducers: {
    setSize(state, action: PayloadAction<number>) {
      state.size = action.payload;
    },
  },
});

export const { setSize } = tableSizeSlice.actions;
export default tableSizeSlice.reducer;
