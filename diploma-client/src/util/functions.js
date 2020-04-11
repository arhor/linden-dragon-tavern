
/** 
 * @param {*} value - any type value
 * @returns {*} the argument passed in
 */
const identity = value => value;

/**
 * @param {Function} prevFun - function to be executed first
 * @param {Function} nextFun - function to be executed last
 * @returns {Function} a composition of passed functions
 */
const simpleCompose = (prevFun, nextFun) => {
  return (...args) => {
    return nextFun(prevFun(...args));
  };
};

/**
 * @param  {...Function} funs - functions to be composed
 * @returns {Function} a composition of passed functions - executes from right to left
 */
export const compose = (...funs) => {
  return funs.reduceRight(simpleCompose, identity);
};

/**
 * @param  {...Function} funs - functions to be composed
 * @returns {Function} a composition of passed functions - executes from left to right
 */
export const pipe = (...funs) => {
  return funs.reduce(simpleCompose, identity);
};
