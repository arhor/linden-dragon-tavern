import { createSlice } from '@reduxjs/toolkit';
import { notEmpty } from '@/utils/core';
import { USER, INITIAL_STATE } from '@/store/user/model';

const userSlice = createSlice({
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
    setProp: (state, { prop, value }) => {
      if (Object.keys(USER).includes(prop)) {
        state[prop] = value ?? INITIAL_STATE[prop];
        localStorage.setItem(prop, state[prop]);
      }
    },
  },
});

export default userSlice.reducer;

export const { resetState, setProp } = userSlice.actions;

export const isAuthenticated = state => notEmpty(state[USER.ACCESS_TOKEN]);

export const getRole = state => state[USER.ROLE];

export const getFirstName = state => state[USER.FIRST_NAME];

export const getLastName = state => state[USER.LAST_NAME];

export const getEmail = state => state[USER.EMAIL];