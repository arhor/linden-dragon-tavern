export function useObjectURL(data, urlConsumer) {
    const blob = new Blob([data]);
    const url = window.URL.createObjectURL(blob);
    urlConsumer(url);
    window.URL.revokeObjectURL(url);
}

export function deserialize(str = '') {
    const result = {};
    const entries = str?.split(';');
    for (let i = 0; i < entries.length; i++) {
        const [name, value] = entries[i].split('=');
        result[name] = value ?? true;
    }
    return result;
}
