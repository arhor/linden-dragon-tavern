import {generateUUID} from '@/utils/coreUtils.js';

export const CSRF_TOKEN = generateUUID();

export const addCsrfToken = (config) => {
    if (['GET', 'HEAD', 'OPTIONS', 'TRACE'].includes(config.method?.toUpperCase())) {
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
};
