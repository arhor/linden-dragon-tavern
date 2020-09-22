import axios from 'axios';

const mutation = {
    SET_MONSTERS: 'SET_MONSTERS',
    SET_MONSTER_DETAILS: 'SET_MONSTER_DETAILS'
};

const actions = {
    load: async ({ commit }) => {
        const address = location.protocol + '//' + location.host + '/api/v1/monsters';

        try {
            const { data } = await axios.get(/*'data/5e-SRD-Monsters.json'*/ address);
            commit(mutation.SET_MONSTERS, data);
        } catch (error) {
            console.error(error);
        }
    },

    loadDetails: async ({ commit }, name) => {
        const { protocol, host } = location;
        const address = `${protocol}//${host}/api/v1/monsters/details/${name}`;
        try {
            const { data } = await axios.get(address);
            commit(mutation.SET_MONSTER_DETAILS, data);
        } catch (error) {
            console.error(error);
        }
    }
};

const mutations = {
    [mutation.SET_MONSTERS]: (state, payload) => {
        state.allMonsters = payload;
    },
    [mutation.SET_MONSTER_DETAILS]: (state, payload) => {
        state.concreteMonster = payload;
    }
};

export default {
    namespaced: true,
    state: {
        allMonsters: [],
        concreteMonster: null
    },
    getters: {},
    actions,
    mutations
};
