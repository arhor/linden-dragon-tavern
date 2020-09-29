export const useLoginGuard = (store) => {
    return (to, from, next) => {
        if (store.getters['auth/isLoggedIn'] ?? false) {
            next();
        } else {
            next({ path: '/' });
        }
    };
};
