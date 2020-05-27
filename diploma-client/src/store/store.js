import { configureStore } from '@reduxjs/toolkit';
import user from '@/store/user';

export default configureStore({
  reducer: {
    user,
  }
});