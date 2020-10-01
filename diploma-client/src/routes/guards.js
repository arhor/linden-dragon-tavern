export const useLoginGuard = (store) => {
    return (to, from, next) => {
        if (store.getters['auth/isLoggedIn'] ?? false) {
            next();
        } else {
            next({ path: '/' });
        }
    };
};

export const useAuthoritiesGuard = (store) => (...requiredAuthorities) => {
    return (to, from, next) => {
        const grantedAuthorities = store.getters['auth/grantedAuthorities'] ?? [];

        let hasAccess = true;
        for (let authority of requiredAuthorities) {
            if (!grantedAuthorities.includes(authority)) {
                hasAccess = false;
                break;
            }
        }

        if (hasAccess) {
            next();
        } else {
            console.log('access denied!');
            next({ path: '/' });
        }
    };
};
