export type Optional<T> = T | null | undefined;

export function useObjectURL(data: BufferSource, urlConsumer: (arg: string) => void) {
    const blob = new Blob([data]);
    const url = window.URL.createObjectURL(blob);
    urlConsumer(url);
    window.URL.revokeObjectURL(url);
}

export function parseJWT(token: string): object {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');

    const payload = JSON.parse(
        decodeURIComponent(
            atob(base64)
                .split('')
                .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
                .join(''),
        ),
    );

    return { ...payload, sub: JSON.parse(payload.sub) };
}

export function generateUUID(): string {
    const uuidPattern = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx';

    return uuidPattern.replace(/[xy]/g, (c) => {
        const r = (Math.random() * 16) | 0;
        const v = c === 'x' ? r : (r & 0x3) | 0x8;
        return v.toString(16);
    });
}

export function refExists<T>(obj: Optional<T>): boolean {
    return !(isUndefined(obj) || isNull(obj));
}

export function isUndefined<T>(value: Optional<T>): boolean {
    return value === void 0;
}

export function isNull<T>(value: Optional<T>): boolean {
    return value === null;
}

export function delay(ms: number) {
    return new Promise((resolve) => setTimeout(resolve, ms));
}
