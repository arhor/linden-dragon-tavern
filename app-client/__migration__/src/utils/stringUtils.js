/**
 * @param {string} [text]
 * @returns {string | null}
 */
export function renderLinebreaksHTML(text) {
    return text?.replace(/(\n)|(\r\n)/g, '<br/>');
}

/**
 * @param {number} number
 * @returns {string}
 */
export function signed(number = 0) {
    const sign = number >= 0 ? '+' : '';
    return `${sign}${number}`;
}

/**
 * @param {string} [str]
 * @returns {string}
 */
export function replaceSpacesWithUnderscore(str) {
    return str?.replace(/ /g, '_')?.toLowerCase() ?? '';
}

/**
 * @param {object} [data]
 * @param {string} [separator]
 * @return {string}
 */
export function serialize(data, separator = ';') {
    let result = '';
    if (data) {
        for (const [name, value] of Object.entries(data)) {
            result += name;
            if (value !== true && value !== 'true') {
                result += `=${value}`;
            }
            result += separator;
        }
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
