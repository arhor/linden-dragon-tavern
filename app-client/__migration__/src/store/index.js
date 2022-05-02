import { configureStore } from '@reduxjs/toolkit';
import user from '@/store/user';

const store = configureStore({
    reducer: {
        user,
    },
    devTools: process.env.NODE_ENV === 'development',
});

export default store;
