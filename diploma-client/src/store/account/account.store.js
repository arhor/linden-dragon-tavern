import { createSlice } from '@reduxjs/toolkit';
import { notEmpty } from '@/utils/core';
import { ACCOUNT, INITIAL_STATE } from '@/store/account/model';

const accountSlice = createSlice({
    name: 'account',
    initialState: {
        ...INITIAL_STATE,
    },
    reducers: {
        resetState: (state) => {
            for (const [prop, initialValue] of INITIAL_STATE) {
                state[prop] = initialValue;
                localStorage.removeItem(prop);
            }
        },
        setProp: (state, { prop, value }) => {
            if (Object.keys(ACCOUNT).includes(prop)) {
                state[prop] = value ?? INITIAL_STATE[prop];
                localStorage.setItem(prop, state[prop]);
            }
        },
    },
});

export default accountSlice.reducer;

export const { resetState, setProp } = accountSlice.actions;

export const isAuthenticated = (state) => notEmpty(state[ACCOUNT.ACCESS_TOKEN]);

export const getAuthorities = (state) => state[ACCOUNT.AUTHORITIES];

export const getFirstName = (state) => state[ACCOUNT.FIRST_NAME];

export const getLastName = (state) => state[ACCOUNT.LAST_NAME];

export const getEmail = (state) => state[ACCOUNT.EMAIL];

export const getAvatarUrl = (state) => state[ACCOUNT.AVATAR_URL];
