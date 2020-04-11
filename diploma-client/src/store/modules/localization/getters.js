const dictionary = ({ locale,dictionaries }) => (label) => {
  const [ data = {} ] =
      dictionaries
          .filter(d => d.lang === locale)
          .map(d => d.data);

  return data[label];
};

export default {
  dictionary,
};
