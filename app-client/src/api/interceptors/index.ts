import { AxiosRequestConfig } from 'axios';

import addCsrfToken from '@/api/interceptors/addCsrfToken';
import addRequestTimeout from '@/api/interceptors/addRequestTimeout';
import { pipe } from '@/utils/function-utils';

export function requestInterceptor(config: AxiosRequestConfig): AxiosRequestConfig {
    return pipe(
        addCsrfToken,
        addRequestTimeout,
    )(config);
}
