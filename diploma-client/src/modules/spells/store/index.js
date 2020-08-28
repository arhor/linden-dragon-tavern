import axios from 'axios';

const state = {
    allSpells: []
};

const getters = {};

const actions = {
    load: async (store) => {
        const address = location.protocol + '//' + location.host + '/api/v1/spells';

        const { data } = await axios.get(/*'data/5e-SRD-Spells.json'*/ address);
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
