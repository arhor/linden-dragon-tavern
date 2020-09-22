/** @param {string} text */
export function processLinebreaks(text) {
    return String(text || '').replace(/(\n)|(\r\n)/g, '<br/>');
}

/** @param {number} value */
export function signed(value) {
    const num = value || 0;
    return `${num >= 0 ? '+' : '-'}${num}`;
}

/** @param {string} str */
export function toPropName(str) {
    return str?.replace(/ /g, '_')?.toLowerCase() ?? '';
}
