import { pipe } from '@/util/functions';
import { addCsrfToken } from '@/api/interceptors/addCsrfToken';

export const requestInterceptor = (config) => {
  return pipe(addCsrfToken)(config);
};
