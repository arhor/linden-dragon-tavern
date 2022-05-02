import { createSlice } from '@reduxjs/toolkit';
import { USER, INITIAL_STATE } from '@/store/user/user.model.js';

const userSlice = createSlice({
    name: 'user',
    initialState: {
        ...INITIAL_STATE,
    },
    reducers: {
        resetState: (state) => {
            for (const [prop, initialValue] of Object.entries(INITIAL_STATE)) {
                state[prop] = initialValue;
            }
        },
        setProp: (state, action) => {
            const { prop, value } = action.payload;
            if (Object.keys(USER).includes(prop)) {
                state[prop] = value ?? INITIAL_STATE[prop];
            }
        },
    },
});

export default userSlice.reducer;

export const { resetState, setProp } = userSlice.actions;

export const isAuthenticated = (state) => state[USER.AUTHENTICATED];

export const getAuthorities = (state) => state[USER.AUTHORITIES];
