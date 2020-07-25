/**
 * @param {*} ref - any reference
 * @returns {boolean} true - if passed reference is not null or undefined
 */
export function refExists(ref) {
    return ref !== undefined && ref !== null;
}

/**
 * @param {string} str - string to check
 * @returns {boolean} true - if passed string exists and not empty
 */
export function notEmpty(str) {
    return refExists(str) && str !== '';
}
