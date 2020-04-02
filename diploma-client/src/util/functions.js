const identity = value => value

const simpleCompose = (prevFun, nextFun) => {
  return (...args) => {
    return prevFun(nextFun(...args))
  }
}

export const compose = (...funs) => funs.reduce(simpleCompose, identity)

export const pipe = (...funs) => funs.reduceRight(simpleCompose, identity)
