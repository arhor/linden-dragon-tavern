import actions from '@/store/modules/localization/actions.js';
import getters from '@/store/modules/localization/getters.js';
import mutations from '@/store/modules/localization/mutations.js';

import { default as russian } from '@/assets/translations/lang-ru.json';

const state = {
  locale: 'RU',
  langs: Object.freeze({
    'RU': { type: 'eager' },
    'EN': { type: 'lazy', load: () => import('@/assets/translations/lang-en.json') },
  }),
  dictionaries: [{
    lang: 'RU',
    data: russian,
  }],
};

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters,
};
