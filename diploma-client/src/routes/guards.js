import { isUndefined } from '@/utils/coreUtils';

/**
 * @see https://github.com/atanas-dev/vue-router-multiguard
 */
function evaluateGuards([currentGuard, ...restGuards], to, from, next) {
    if (isUndefined(currentGuard)) {
        next();
    } else {
        currentGuard(to, from, (nextArg) => {
            if (isUndefined(nextArg)) {
                evaluateGuards(restGuards, to, from, next);
            } else {
                next(nextArg);
            }
        });
    }
}

export function composeGuards(...guards) {
    return (to, from, next) => evaluateGuards(guards, to, from, next);
}

export const createLoginGuard = (store) => {
    return (to, from, next) => {
        if (store.getters['auth/isLoggedIn'] ?? false) {
            next();
        } else {
            next({ path: '/' });
        }
    };
};

export const createAuthoritiesGuard = (store) => (...requiredAuthorities) => {
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
