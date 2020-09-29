import { pipe } from '@/utils/functionUtils.js';
import { addCsrfToken } from '@/api/interceptors/addCsrfToken.js';

export const requestInterceptor = (config) => {
    return pipe(addCsrfToken)(config);
};
