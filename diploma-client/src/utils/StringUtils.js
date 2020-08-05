/**
 * Replaces line endings with <br/> element to display correctly in html.
 *
 * @param {string} text
 */
export function processLinebreaks(text) {
    return String(text || '').replace(/(\n)|(\r\n)/g, '<br/>');
}

/**
 * Adds prefix-sign to passed number.
 *
 * @param {Number} num
 */
export function signed(value) {
    const num = value || 0;
    return `${num >= 0 ? '+' : '-'}${num}`;
}

/**
 * Converts passed string to JS valid property name.
 *
 * @param {String} str
 */
export function toPropName(str) {
    return (str || '').replace(/ /g, '_').toLowerCase();
}

/**
 * Stub function to prevent eslint ebat' mne mozgi with 'default export'.
 */
export function stub() {}
