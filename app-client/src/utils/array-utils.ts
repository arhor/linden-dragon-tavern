export function notEmptyArray(items: any[]): boolean {
    return !isEmptyArray(items);
}

export function isEmptyArray(items: any): boolean {
    return Array.isArray(items) && items.length === 0;
}

export function commaSeparate(items?: any[] | null): string {
    return items?.join(', ') ?? '';
}
