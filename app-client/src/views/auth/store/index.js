import authService from '@/views/auth/services/AuthService.js';
import {useLocalStoragePlugin} from '@/utils/storeUtils.js';

const mutation = {
    SET_SESSION_DATA: 'SET_SESSION_DATA',
    INVALIDATE_SESSION: 'INVALIDATE_SESSION',
};

export default {
    namespaced: true,
    state: {
        username: null,
        authenticated: false,
        authorities: [],
    },
    getters: {
        isLoggedIn: (state) => state.authenticated,
        authorityNames: (state) => state.authorities.map((a) => a.authority),
    },
    actions: {
        signIn: async ({ commit }, { username, password }) => {
            try {
                const authorities = await authService.login({ username, password });
                commit(mutation.SET_SESSION_DATA, { username, authorities });
                return true;
            } catch (e) {
                commit(mutation.INVALIDATE_SESSION);
                return false;
            }
        },
        signOut: async ({ commit }) => {
            await authService.logout();
            commit(mutation.INVALIDATE_SESSION);
        },
    },
    mutations: {
        [mutation.SET_SESSION_DATA]: (state, { username, authorities }) => {
            state.username = username;
            state.authorities = authorities;
            state.authenticated = true;
        },
        [mutation.INVALIDATE_SESSION]: (state) => {
            state.username = null;
            state.authorities = [];
            state.authenticated = false;
        },
    },
    plugins: [useLocalStoragePlugin('auth')],
};
