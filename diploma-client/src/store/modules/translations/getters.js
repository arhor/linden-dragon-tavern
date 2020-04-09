const getDictionary = (state) => (lang, label) => {
  const dictionary = state.dictionaries.find(d => d.lang === lang);

  if (dictionary === null || dictionary === undefined) {
    return null;
  }

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
