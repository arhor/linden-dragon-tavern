import { addCsrfToken } from '@/api/interceptors/addCsrfToken.js';
import { compose } from '@/utils/functionUtils.js';

/** @param {import('axios').AxiosRequestConfig} config */
export const requestInterceptor = (config) => {
    return compose(addCsrfToken)(config);
};
