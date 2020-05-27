import { createSlice } from '@reduxjs/toolkit';
import { notEmpty } from '@/utils/core';

const ACCESS_TOKEN = 'accessToken';
const ROLE = 'role';

export const userSlice = createSlice({
  name: 'user',
  initialState: {
    [ACCESS_TOKEN]: '',
    [ROLE]: '',
  },
  reducers: {
    setAccessToken: (state, action) => {
      state[ACCESS_TOKEN] = action.payload;
      localStorage.setItem(ACCESS_TOKEN, state[ACCESS_TOKEN]);
    },
  },
});

export const { setAccessToken } = userSlice.actions;

export const isAuthenticated = state => notEmpty(state[ACCESS_TOKEN]);

export const getRole = state => state[ROLE];

export default userSlice.reducer;