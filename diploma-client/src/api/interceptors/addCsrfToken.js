import { generateUUID } from '@/utils/coreUtils.js';
import sharedLib from '@/lib/diploma-shared.js';

const { CsrfUtils } = sharedLib.org.arhor.diploma;

const CSRF_TOKEN = generateUUID();

export const addCsrfToken = (config) => {
    if (CsrfUtils.SAFE_METHODS.includes(config.method)) {
        return config;
    }

    const { headers = {}, ...restConfig } = config;

    return {
        headers: {
            ...headers,
            [CsrfUtils.CSRF_HEADER_NAME]: CSRF_TOKEN,
        },
        ...restConfig,
    };
};
