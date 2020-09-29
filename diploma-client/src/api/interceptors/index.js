import { pipe } from '@/utils/functionUtils';
import { addCsrfToken } from '@/api/interceptors/addCsrfToken';

export const requestInterceptor = (config) => {
    return pipe(addCsrfToken)(config);
};
