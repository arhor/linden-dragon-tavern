import axios from 'axios';

const state = {
    allSpells: []
};

const getters = {};

const actions = {
    load: async store => {
        const { data } = await axios.get('data/5e-SRD-Spells.json');
        store.commit('SET_SPELLS', data);
    }
};

const mutations = {
    SET_SPELLS: (state, payload) => {
        state.allSpells = payload;
    }
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
};
