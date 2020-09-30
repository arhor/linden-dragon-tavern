/**
 * @template T
 * @param value {T}
 * @return {T}
 */
export const identity = (value) => value;

/**
 * @template T
 * @param prevFun {function(...T): T}
 * @param nextFun {function(...T): T}
 * @return {function(...T): T}
 */
const simpleCompose = (prevFun, nextFun) => {
    return (...args) => {
        return nextFun(prevFun(...args));
    };
};

/**
 * @template T
 * @param functions {...function(T): T}
 * @return {function(T): T}
 */
export function compose(...functions) {
    return functions.reduceRight(simpleCompose, identity);
}

/**
 * @template T
 * @param functions {...function(T): T}
 * @return {function(T): T}
 */
export function pipe(...functions) {
    return functions.reduce(simpleCompose, identity);
}
