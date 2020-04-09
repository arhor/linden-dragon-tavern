import Vue from 'vue'
import Vuex from 'vuex'
import modules from '@/store/modules'

Vue.use(Vuex)

export default new Vuex.Store({
  modules,

  state: {
    currLang: 'RU',
    theme: {
      dark: false,
    },
  },

  mutations: {
    SWITCH_DARK_MODE: (state) => {
      state.theme.dark = !state.theme.dark
    },
  },

  actions: {
    changeTheme: ({ commit }) => commit('SWITCH_DARK_MODE'),
  },

  getters: {
  },
});
