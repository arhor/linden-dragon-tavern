import Vue from 'vue';
import Vuex from 'vuex';

import abilities from '@/modules/ability/store';
import auth from '@/modules/auth/store';
import maps from '@/modules/maps/store';
import monsters from '@/modules/monsters/store';
import skills from '@/modules/skill/store';
import spells from '@/modules/spells/store';

Vue.use(Vuex);

const mutation = {
    SWITCH_THEME: 'SWITCH_THEME',
};

const store = new Vuex.Store({
    state: {
        dark: true,
    },
    getters: {},
    actions: {
        switchTheme: ({ commit }) => {
            commit(mutation.SWITCH_THEME);
        },
    },
    mutations: {
        [mutation.SWITCH_THEME]: (state) => {
            state.dark = !state.dark;
        },
    },
    modules: {
        abilities,
        auth,
        maps,
        monsters,
        skills,
        spells,
    },
});

export default store;
