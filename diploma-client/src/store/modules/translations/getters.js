const getDictionary = (state, actions) => (lang, label) => {

  const dictionary = state.dictionaries.find(d => d.lang === lang);

  return dictionary.data[label];
};

const getTranslationFile = (state) => (label) => {
  for (const [, lang] of Object.entries(state.langs)) {
    if (lang.label === label) {
      return lang.file;
    }
  }
  return null;
};

export default {
  getDictionary,
  getTranslationFile,
};
