import { csrfToken } from '@/api/csrfToken';

const SAFE_METHODS = ['get', 'head', 'options', 'trace'];
const CSRF_HEADER_NAME = 'x-csrf-token';

export const addCsrfToken = (config) => {
  if (SAFE_METHODS.includes(config.method)) {
    return config;
  }

  const { headers = {}, ...restConfig } = config;

  return {
    headers: {
      ...headers,
      [CSRF_HEADER_NAME]: csrfToken,
    },
    ...restConfig,
  };
};
