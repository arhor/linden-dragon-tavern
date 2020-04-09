import Vue from 'vue'
import Vuex from 'vuex'
import modules from '@/store/modules'

Vue.use(Vuex)

const store = new Vuex.Store({
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

for (const [name, module] of Object.entries(modules)) {
  store.registerModule(name, module);
}

export default store;