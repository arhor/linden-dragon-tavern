import { createSlice } from '@reduxjs/toolkit';
import { notEmpty } from '@/utils/core';

const ACCESS_TOKEN = 'accessToken';

export const authSlice = createSlice({
  name: 'auth',
  initialState: {
    [ACCESS_TOKEN]: '',
  },
  reducers: {
    setAccessToken: (state, action) => {
      state[ACCESS_TOKEN] = action.payload;
      localStorage.setItem(ACCESS_TOKEN, state[ACCESS_TOKEN]);
    },
  },
});

export const { setAccessToken } = authSlice.actions;

export const isAuthenticated = (state) => notEmpty(state[ACCESS_TOKEN]);

export default authSlice.reducer;