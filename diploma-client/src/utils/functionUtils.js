/**
 * @template T
 * @param value {T}
 * @return {T}
 */
export const identity = (value) => value;

/**
 * @template T
 * @param prevFun {function(T): T}
 * @param nextFun {function(T): T}
 * @return {function(T): T}
 */
export const simpleCompose = (prevFun, nextFun) => {
    return (arg) => {
        return nextFun(prevFun(arg));
    };
};

/**
 * @template T
 * @param functions {(function(T): T)[]}
 * @return {function(T): T}
 */
export const compose = (...functions) => {
    return functions.reduceRight(simpleCompose, identity);
};

/**
 * @template T
 * @param functions {(function(T): T)[]}
 * @return {function(T): T}
 */
export const pipe = (...functions) => {
    return functions.reduce(simpleCompose, identity);
};

/**
 * @template R
 * @param functions {(function(...[*]): R)[]} array of functions which must have the same signature
 * @param reducer {function(R, R): R} reducer function combining two values into the one
 * @param seed {R} initial value
 * @return {function(...[*]): R}
 */
export const combineReduce = ([...functions], reducer, seed) => {
    return (...args) => {
        return functions.map((fn) => fn(...args)).reduce(reducer, seed);
    };
};
