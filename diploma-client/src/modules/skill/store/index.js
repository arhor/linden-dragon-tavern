import axios from 'axios';

const mutation = {
    SET_SKILLS: 'SET_SKILLS'
};

const actions = {
    load: async ({ commit }) => {
        const { data } = await axios.get('data/5e-SRD-Skills.json');
        commit(mutation.SET_SKILLS, data);
    }
};

const mutations = {
    [mutation.SET_SKILLS]: (state, payload) => {
        state.all = payload;
    }
};

export default {
    namespaced: true,
    state: {
        all: []
    },
    getters: {},
    actions,
    mutations
};
