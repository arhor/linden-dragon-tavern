import axios from 'axios';

const state = {
    allMonsters: []
};

const getters = {};

const actions = {
    load: async (store) => {
        // const address = location.protocol + "//" + location.host + '/api/v1/monsters'
        try {
            const { data } = await axios.get('data/5e-SRD-Monsters.json');
            store.commit('SET_MONSTERS', data);
        } catch (error) {
            console.error(error);
        }
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
