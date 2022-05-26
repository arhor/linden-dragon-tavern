import { AxiosRequestConfig } from 'axios';

import { addCsrfToken } from '@/api/interceptors/addCsrfToken';
import { compose } from '@/utils/function-utils';

export function requestInterceptor(config: AxiosRequestConfig): AxiosRequestConfig {
    return compose(
        addCsrfToken,
    )(config);
}
