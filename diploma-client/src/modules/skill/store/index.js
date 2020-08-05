/* eslint-disable no-param-reassign */
import axios from 'axios';

const state = {
    all: []
};

const getters = {};

const actions = {
    async load({ commit }) {
        const { data } = await axios.get('data/5e-SRD-Skills.json');
        commit('SET_SKILLS', data);
    }
};

const mutations = {
    SET_SKILLS(state, payload) {
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
