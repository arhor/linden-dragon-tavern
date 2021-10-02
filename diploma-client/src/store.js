import Vue from 'vue';
import Vuex from 'vuex';

import abilities from '@/views/ability/store';
import auth from '@/views/auth/store';
import characterStats from '@/views/characters/store';
import maps from '@/views/maps/store';
import monsters from '@/views/monsters/store';
import skills from '@/views/skill/store';
import spells from '@/views/spells/store';

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
        characterStats,
        auth,
        maps,
        monsters,
        skills,
        spells,
    },
});

export default store;
