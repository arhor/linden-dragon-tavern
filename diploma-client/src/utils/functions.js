
const identity = value => value;

const simpleCompose = (prevFun, nextFun) => {
  return (...args) => {
    return nextFun(prevFun(...args));
  };
};

export function compose(...funs) {
  return funs.reduceRight(simpleCompose, identity);
};

export function pipe(...funs) {
  return funs.reduce(simpleCompose, identity);
};