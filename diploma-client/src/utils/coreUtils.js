/**
 * Provides execution context for ObjectURL consuming function and revokes ObjectURL after usage.
 *
 * @param data {BufferSource}
 * @param urlConsumer {function(string): void}
 */
export function useObjectURL(data, urlConsumer) {
    const blob = new Blob([data]);
    const url = window.URL.createObjectURL(blob);
    urlConsumer(url);
    window.URL.revokeObjectURL(url);
}

/**
 * Parses JWT string into a JS object.
 *
 * @param token {string}
 * @return {object}
 */
export function parseJWT(token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');

    const payload = JSON.parse(
        decodeURIComponent(
            atob(base64)
                .split('')
                .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
                .join(''),
        ),
    );

    return { ...payload, sub: JSON.parse(payload.sub) };
}

/**
 * Generates string representation of the UUID.
 *
 * @return {string}
 */
export function generateUUID() {
    const uuidPattern = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx';

    return uuidPattern.replace(/[xy]/g, (c) => {
        const r = (Math.random() * 16) | 0;
        const v = c === 'x' ? r : (r & 0x3) | 0x8;
        return v.toString(16);
    });
}

/**
 * @param obj {*}
 * @return {boolean}
 */
export function refExists(obj) {
    return obj !== void 0 && obj !== null;
}
