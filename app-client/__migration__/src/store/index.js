import { createContext, useContext } from 'react';

import AppStore from '@/store/app';
import UserStore from '@/store/user';

export const store = Object.freeze({
    app: new AppStore(),
    user: new UserStore(),
});

export const StoreContext = createContext(store);

export const useStore = () => useContext(StoreContext);
