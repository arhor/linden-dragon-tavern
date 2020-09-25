/** @param {string} text */
export function renderLinebreaksHTML(text = '') {
    return text?.replace(/(\n)|(\r\n)/g, '<br/>');
}

/** @param {number} value */
export function signed(value) {
    const num = value || 0;
    return `${num >= 0 ? '+' : '-'}${num}`;
}

/** @param {string} str */
export function replaceSpacesWithUnderscore(str = '') {
    return str?.replace(/ /g, '_')?.toLowerCase() ?? '';
}

/** @param {string} str */
export function deserialize(str = '') {
    const result = {};
    const entries = str?.split(';');
    for (let i = 0; i < entries.length; i++) {
        const [name, value] = entries[i].split('=');
        result[name] = value ?? true;
    }
    return result;
}
