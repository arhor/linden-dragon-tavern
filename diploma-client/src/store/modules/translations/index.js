import actions from '@/store/modules/translations/actions.js';
import getters from '@/store/modules/translations/getters.js';
import mutations from '@/store/modules/translations/mutations.js';

const state = {
  langs: Object.freeze({
    russian: {
      label: 'RU',
      file: 'translations/lang-ru.json',
    },
  }),
  dictionaries: [],
};

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters,
};
