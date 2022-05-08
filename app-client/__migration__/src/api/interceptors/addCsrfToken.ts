import { AxiosRequestConfig } from 'axios';

import { generateUUID } from '@/utils/coreUtils.js';

export const CSRF_TOKEN = generateUUID();

export function addCsrfToken(config: AxiosRequestConfig): AxiosRequestConfig {
    const requestMethod = config.method?.toUpperCase();

    if (requestMethod && ['GET', 'HEAD', 'OPTIONS', 'TRACE'].includes(requestMethod)) {
        return config;
    }

    const { headers = {}, ...restConfig } = config;

    return {
        headers: {
            ...headers,
            'x-csrf-token': CSRF_TOKEN,
        },
        ...restConfig,
    };
}
