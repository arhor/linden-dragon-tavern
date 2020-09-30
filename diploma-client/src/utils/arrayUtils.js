/** @param {Array} items */
export function notEmptyArray(items) {
    return !isEmptyArray(items);
}

/** @param {*[]} items */
export function isEmptyArray(items) {
    return Array.isArray(items) && items.length === 0;
}

/** @param {Array} items */
export function commaSeparate(items) {
    return items?.join(', ') ?? '';
}
