/** @param {string} [text] */
export function renderLinebreaksHTML(text) {
    return text?.replace(/(\n)|(\r\n)/g, '<br/>');
}

/** @param {number} number */
export function signed(number = 0) {
    const sign = number >= 0 ? '+' : '';
    return `${sign}${number}`;
}

/** @param {string} [str] */
export function replaceSpacesWithUnderscore(str) {
    return str?.replace(/ /g, '_')?.toLowerCase() ?? '';
}

/**
 * @param {object} [obj]
 * @param {string} [separator]
 * @return {string}
 */
export function serialize(obj, separator = ';') {
    let result = '';
    for (const [name, value] of Object.entries(obj ?? {})) {
        result += name;
        if (value !== true && value !== 'true') {
            result += `=${value}`;
        }
        result += separator;
    }
    return result;
}

/**
 * @param {string} [str]
 * @param {string} [separator]
 * @return {object}
 */
export function deserialize(str, separator = ';') {
    const result = {};
    const entries = str?.split(separator) ?? [];

    for (const entry of entries) {
        if (entry === '') {
            continue;
        }
        const [name, value] = entry.split('=');
        result[name] = value ?? true;
    }
    return result;
}
