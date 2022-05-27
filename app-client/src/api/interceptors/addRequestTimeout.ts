import { AxiosRequestConfig } from 'axios';
import log from 'loglevel';

const DEFAULT_TIMEOUT = 10_000;

const timeouts: ReadonlyMap<string, number> = new Map([
    ['/api/v1/creatures', 1_000],
]);

export default function addRequestTimeout(config: AxiosRequestConfig): AxiosRequestConfig {
    const { timeout, ...restConfig } = config;
    const requestUrl = config.baseURL + '/' + config.url;
    const timeoutToUse = timeout || timeouts.get(requestUrl) || DEFAULT_TIMEOUT;

    log.debug('Using request timeout %s for the url: %s/%s', timeoutToUse, config.baseURL, config.url);

    return {
        timeout: timeoutToUse,
        ...restConfig,
    };
}
