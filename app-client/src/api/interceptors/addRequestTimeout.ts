import { AxiosRequestConfig } from 'axios';
import log from 'loglevel';

import { Optional } from '@/utils/core-utils';

const DEFAULT_TIMEOUT = 10_000;

const timeouts: ReadonlyMap<Optional<string>, number> = new Map([
    ['v1/creatures', 1_000],
]);

export default function addRequestTimeout(config: AxiosRequestConfig): AxiosRequestConfig {
    const { timeout, ...restConfig } = config;
    const timeoutToUse = timeout || timeouts.get(config.url) || DEFAULT_TIMEOUT;

    log.debug('Using request timeout %s for the url: %s/%s', timeoutToUse, config.baseURL, config.url);

    return {
        timeout: timeoutToUse,
        ...restConfig,
    };
}
