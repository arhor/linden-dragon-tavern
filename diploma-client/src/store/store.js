import { configureStore } from '@reduxjs/toolkit';
import account from '@/store/account';

export default configureStore({
    reducer: {
        account,
    },
});
