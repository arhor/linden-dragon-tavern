import { pipe } from '@/utils/functions';
import { addCsrfToken } from '@/services/api/interceptors/addCsrfToken';

export const requestInterceptor = (config) => {
    return pipe(addCsrfToken)(config);
};
