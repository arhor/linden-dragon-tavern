/**
 * @param {*} ref - any reference
 * @returns {boolean} true - if passed reference is not null or undefined
 */
export const refExists = (ref) => {
  return ref !== undefined
      && ref !== null;
};
