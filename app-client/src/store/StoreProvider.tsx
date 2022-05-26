import { createContext, ReactNode, useContext } from 'react';

import { createStore, RootStore } from '@/store/RootStore';

export const store = createStore();

export const StoreContext = createContext<Readonly<RootStore>>(store);

export const StoreProvider = (props: { children: ReactNode }) => (
    <StoreContext.Provider value={store}>
        {props.children}
    </StoreContext.Provider>
);

export function useStore() {
    return useContext(StoreContext);
}
