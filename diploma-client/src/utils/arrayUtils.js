/** @param {Array} items */
export function notEmptyArray(items) {
    return !isEmptyArray(items);
}

/** @param {Array} items */
export function isEmptyArray(items) {
    return items?.length === 0;
}

/** @param {Array} items */
export function commaSeparate(items) {
    return items?.join(',') ?? '';
}
