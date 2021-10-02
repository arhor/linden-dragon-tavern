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
 * @return {string}
 */
export function serialize(obj) {
    let result = '';
    for (const [name, value] of Object.entries(obj ?? {})) {
        result += name;
        if (value !== true && value !== 'true') {
            result += `=${value}`;
        }
        result += ';';
    }
    return result;
}

/**
 * @param {string} [str]
 * @return {object}
 */
export function deserialize(str) {
    const result = {};
    const entries = str?.split(';') ?? [];

    for (const entry of entries) {
        if (entry === '') {
            continue;
        }
        const [name, value] = entry.split('=');
        result[name] = value ?? true;
    }
    return result;
}
