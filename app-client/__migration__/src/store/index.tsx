import { createContext, useContext, ReactNode } from 'react';

import AppStore from '@/store/app';
import NotificationStore from '@/store/notification';
import UserStore from '@/store/user';

export const store = Object.freeze({
    app: new AppStore(),
    notification: new NotificationStore(),
    user: new UserStore(),
});

const StoreContext = createContext(store);

type StoreProviderProps = {
    children: ReactNode;
};

export const StoreProvider = ({ children }: StoreProviderProps) => (
    <StoreContext.Provider value={store}>
        {children}
    </StoreContext.Provider>
);

export const useStore = () => useContext(StoreContext);
