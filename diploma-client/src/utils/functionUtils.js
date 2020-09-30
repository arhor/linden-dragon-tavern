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
 * @param functions {function(T): T}
 * @return {function(T): T}
 */
export const compose = (...functions) => {
    return functions.reduceRight(simpleCompose, identity);
};

/**
 * @template T
 * @param functions {function(T): T}
 * @return {function(T): T}
 */
export const pipe = (...functions) => {
    return functions.reduce(simpleCompose, identity);
};
