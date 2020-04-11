import { refExists } from '@/util/helpers.js';

const loadLang = async ({ commit, state, getters }, lang) => {
  const dictionary = getters['dictionary'](lang);

  if (refExists(dictionary)) {
    return;
  }

  const { load } = state.langs[lang];

  const data = await load();

  if (data !== undefined && data !== null) {
    commit('SET_LANG', { lang, data });
    return;
  }
  throw new Error(`Translations package for '${lang}' is not found`);
};

export default {
  loadLang,
};
