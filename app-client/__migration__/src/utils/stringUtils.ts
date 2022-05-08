export function renderLinebreaksHTML(text?: string): string | undefined {
    return text?.replace(/(\n)|(\r\n)/g, '<br/>');
}

export function signed(value: number): string {
    const sign = value >= 0 ? '+' : '';
    return `${sign}${value}`;
}

export function replaceSpacesWithUnderscore(text?: string): string {
    return text?.replace(/ /g, '_')?.toLowerCase() ?? '';
}

export function serialize(data?: any, separator = ';'): string {
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

export function deserialize(text: string | null | undefined, separator = ';'): any {
    const result: any = {};
    const entries = text?.split(separator) ?? [];

    for (const entry of entries) {
        if (entry === '') {
            continue;
        }
        const [name, value] = entry.split('=');
        result[name] = value ?? true;
    }
    return result;
}
