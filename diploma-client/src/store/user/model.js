export const USER = Object.freeze({
  ACCESS_TOKEN: 'accessToken',
  ROLE: 'role',
  FIRST_NAME: 'firstName',
  LAST_NAME: 'lastName',
  EMAIL: 'email',
});

export const INITIAL_STATE = Object.freeze({
  [USER.ACCESS_TOKEN]: '',
  [USER.ROLE]: '',
  [USER.FIRST_NAME]: '',
  [USER.LAST_NAME]: '',
  [USER.EMAIL]: '',
});