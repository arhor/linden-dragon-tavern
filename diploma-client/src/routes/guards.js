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
        const getter = store.getters['auth/isLoggedIn'];
        console.debug('GUARD: ', getter);
        if (getter ?? false) {
            next();
        } else {
            next({ path: '/' });
        }
    };
};

export const createAuthoritiesGuard = (store) => (...requiredAuthorities) => {
    return (to, from, next) => {
        const authorityNames = store.getters['auth/authorityNames'] ?? [];

        let hasAccess = true;
        for (let requiredAuthority of requiredAuthorities) {
            if (!authorityNames.includes(requiredAuthority)) {
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

export function createLangGuard(langLoader) {
    return (to, from, next) => {
        if (to.params.lang) {
            langLoader(to.params.lang).then(() => next());
        } else {
            next();
        }
    };
}
