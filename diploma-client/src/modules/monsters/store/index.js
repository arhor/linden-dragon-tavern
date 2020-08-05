import axios from 'axios';

const state = {
    allMonsters: []
};

const getters = {};

const actions = {
    load: async store => {
        const { data } = await axios.get('data/5e-SRD-Monsters.json');
        store.commit('SET_MONSTERS', data);
    }
};

const mutations = {
    SET_MONSTERS: (state, payload) => {
        state.allMonsters = payload;
    }
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
};
