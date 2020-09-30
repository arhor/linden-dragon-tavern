import { addCsrfToken } from '@/api/interceptors/addCsrfToken.js';
import { compose } from '@/utils/functionUtils.js';

export const requestInterceptor = (config) => {
    return compose(addCsrfToken)(config);
};
