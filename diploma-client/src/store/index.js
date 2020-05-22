import { configureStore } from '@reduxjs/toolkit';
import auth from '@/store/auth';

export default configureStore({
  reducer: {
    auth
  }
});