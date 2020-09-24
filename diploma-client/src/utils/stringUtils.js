/** @param {string} text */
export function renderLinebreaksHTML(text) {
    return text?.replace(/(\n)|(\r\n)/g, '<br/>');
}

/** @param {number} value */
export function signed(value) {
    const num = value || 0;
    return `${num >= 0 ? '+' : '-'}${num}`;
}

/** @param {string} str */
export function replaceSpacesWithUnderscore(str) {
    return str?.replace(/ /g, '_')?.toLowerCase() ?? '';
}
