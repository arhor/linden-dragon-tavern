export type UnaryOperator<T> = (arg: T) => T;

export function identity<T>(value: T): T {
    return value;
}

export function simpleCompose<T>(prev: UnaryOperator<T>, next: UnaryOperator<T>): UnaryOperator<T> {
    return (arg: T) => {
        return next(prev(arg));
    };
}

export function compose<T>(...functions: UnaryOperator<T>[]): UnaryOperator<T> {
    return functions.reduceRight(simpleCompose, identity);
}

export function pipe<T>(...functions: UnaryOperator<T>[]): UnaryOperator<T> {
    return functions.reduce(simpleCompose, identity);
}
