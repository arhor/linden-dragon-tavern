import axios from 'axios';

const state = {
    all: []
};

const getters = {};

const actions = {
    async load({ commit }) {
        const { data } = await axios.get('data/5e-SRD-Ability-Scores.json');
        commit('SET_ABILITIES', data);
    }
};

const mutations = {
    SET_ABILITIES(state, payload) {
        state.all = payload;
    }
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
};
