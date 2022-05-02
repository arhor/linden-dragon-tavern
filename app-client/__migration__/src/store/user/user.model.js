/**
 * @readonly @enum {string}
 */
export const USER = Object.freeze({
    AUTHENTICATED: 'authenticated',
    AUTHORITIES: 'authorities',
});

/**
 * @readonly
 */
export const INITIAL_STATE = Object.freeze({
    [USER.AUTHENTICATED]: false,
    [USER.AUTHORITIES]: [],
});
