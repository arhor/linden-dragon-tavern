import { ComponentType } from 'react';

export function withInit<T extends ComponentType<unknown>>(
    factory: () => Promise<{ default: T }>,
    initializers: (() => void)[] = [],
) {
    return () => {
        for (const initializer of initializers) {
            initializer();
        }
        return factory();
    };
}
