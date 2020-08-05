import Vue from 'vue';
import Vuex from 'vuex';

import abilities from '@/modules/ability/store';
import maps from '@/modules/maps/store';
import monsters from '@/modules/monsters/store';
import skills from '@/modules/skill/store';
import spells from '@/modules/spells/store';

Vue.use(Vuex);

const store = new Vuex.Store({
    modules: {
        abilities,
        maps,
        monsters,
        skills,
        spells
    }
});

export default store;
