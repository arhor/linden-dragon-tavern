/**
 * Defines is passed array not empty nor null (undefined).
 *
 * @param {Array} items
 */
export function notEmptyArray(items) {
    return !isEmptyArray(items);
}

export function isEmptyArray(items) {
    return items !== null && items !== void 0 && items.length === 0;
}

/**
 * Traverse through passed array reducing it to comma separated string of items.
 *
 * @param {Array} items
 */
export function commaSeparate(items) {
    return (items || []).reduce((prev, curr) => `${prev}, ${curr}`);
}

/**
 * Stub function to prevent eslint ebat' mne mozgi with 'default export'.
 */
export function stub() {}
