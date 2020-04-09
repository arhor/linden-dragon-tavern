const SET_LANG = (state, { lang, data }) => {
  if (!state.dictionaries.map(d => d.lang).includes(lang)) {
    state.dictionaries.push({
      lang,
      data,
    });
  }
};

export default {
  SET_LANG,
};
