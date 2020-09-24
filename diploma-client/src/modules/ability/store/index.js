import axios from 'axios';

export const action = {
    load: 'load',
};

const mutation = {
    SET_ABILITIES: 'SET_ABILITIES',
};

export default {
    namespaced: true,
    state: {
        all: [],
    },
    getters: {},
    actions: {
        [action.load]: async ({ commit }) => {
            const { data } = await axios.get('data/5e-SRD-Ability-Scores.json');
            commit(mutation.SET_ABILITIES, data);
        },
    },
    mutations: {
        [mutation.SET_ABILITIES]: (state, payload) => {
            state.all = payload;
        },
    },
};
