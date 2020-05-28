import { createSlice } from '@reduxjs/toolkit';
import { notEmpty } from '@/utils/core';
import { USER, INITIAL_STATE } from '@/store/user/model';

export const userSlice = createSlice({
  name: 'user',
  initialState: {
    ...INITIAL_STATE
  },
  reducers: {
    resetState: state => {
      for (const [prop, initialValue] of INITIAL_STATE) {
        state[prop] = initialValue;
        localStorage.removeItem(prop);
      }
    },
    setAccessToken: (state, action) => {
      state[USER.ACCESS_TOKEN] = action.payload;
      localStorage.setItem(USER.ACCESS_TOKEN, state[USER.ACCESS_TOKEN]);
    },
  },
});

export const { setAccessToken } = userSlice.actions;

export const isAuthenticated = state => notEmpty(state[USER.ACCESS_TOKEN]);

export const getRole = state => state[USER.ROLE];

export default userSlice.reducer;