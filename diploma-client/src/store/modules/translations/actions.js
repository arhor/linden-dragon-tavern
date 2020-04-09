import axios from 'axios';

const loadLang = async ({ commit, getters }, lang) => {
  const file = getters['getTranslationFile'](lang);
  if (file !== null) {
    const { data } = await axios.get(file);
    commit('SET_LANG', { lang, data });
    return;
  }
  throw new Error(`Translations package for '${lang}' is not found`);
};

export default {
  loadLang,
}
