export const ACCOUNT = Object.freeze({
    ACCESS_TOKEN: 'accessToken',
    AUTHORITIES: 'authorities',
    FIRST_NAME: 'firstName',
    LAST_NAME: 'lastName',
    EMAIL: 'email',
    AVATAR_URL: 'avatarUrl',
});

export const INITIAL_STATE = Object.freeze({
    [ACCOUNT.ACCESS_TOKEN]: '',
    [ACCOUNT.AUTHORITIES]: [],
    [ACCOUNT.FIRST_NAME]: '',
    [ACCOUNT.LAST_NAME]: '',
    [ACCOUNT.EMAIL]: '',
    [ACCOUNT.AVATAR_URL]: '',
});
