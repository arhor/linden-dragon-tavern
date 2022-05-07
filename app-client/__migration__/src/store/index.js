import React, { createContext, useContext } from 'react';

import PropTypes from 'prop-types';

import AppStore from '@/store/app';
import NotificationStore from '@/store/notification';
import UserStore from '@/store/user';

const store = Object.freeze({
    app: new AppStore(),
    notification: new NotificationStore(),
    user: new UserStore(),
});

const StoreContext = createContext(store);

StoreProvider.propTypes = {
    children: PropTypes.node,
};

export function StoreProvider({ children }) {
    return React.createElement(
        StoreContext.Provider,
        { value: store },
        children
    );
}

export const useStore = () => useContext(StoreContext);
