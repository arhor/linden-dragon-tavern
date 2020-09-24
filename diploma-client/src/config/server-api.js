const DEFAULT_API_URL = 'http://localhost:8080';

export const SERVER_API_URL =
    process.env.NODE_ENV === 'development'
        ? process.env.API_URL ?? DEFAULT_API_URL
        : `${location.protocol}//${location.host}`;
