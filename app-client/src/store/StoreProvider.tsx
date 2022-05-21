import { createContext, ReactNode, useContext } from 'react';

import { createStore, RootStore } from '@/store/RootStore';

const StoreContext = createContext<Readonly<RootStore>>({} as RootStore);

export const StoreProvider = (props: { children: ReactNode }) => (
    <StoreContext.Provider value={createStore()}>
        {props.children}
    </StoreContext.Provider>
);

export function useStore() {
    return useContext(StoreContext);
}
