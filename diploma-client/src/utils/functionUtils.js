const identity = (value) => value;

const simpleCompose = (prevFun, nextFun) => {
    return (...args) => {
        return nextFun(prevFun(...args));
    };
};

export function compose(...functions) {
    return functions.reduceRight(simpleCompose, identity);
}

export function pipe(...functions) {
    return functions.reduce(simpleCompose, identity);
}
