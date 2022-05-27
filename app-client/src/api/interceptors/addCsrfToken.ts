import { AxiosRequestConfig } from 'axios';

import { generateUUID } from '@/utils/core-utils';

export const CSRF_TOKEN = generateUUID();

const SAFE_METHODS: Readonly<Record<string, boolean>> = {
    GET: true,
    HEAD: true,
    OPTIONS: true,
    TRACE: true,
};

export default function addCsrfToken(config: AxiosRequestConfig): AxiosRequestConfig {
    const requestMethod = config.method?.toUpperCase();

    if (requestMethod && SAFE_METHODS[requestMethod]) {
        return config;
    }

    const { headers = {}, ...restConfig } = config;

    return {
        headers: {
            ...headers,
            'X-XSRF-TOKEN': CSRF_TOKEN,
        },
        ...restConfig,
    };
}
