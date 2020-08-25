import axios from 'axios';

export default {
    namespaced: true,
    state: {
        allMonsters: [],
        concreteMonster: null
    },
    getters: {},
    actions: {
        load: async (store) => {
            const address = location.protocol + '//' + location.host + '/api/v1/monsters';

            try {
                const { data } = await axios.get(/*'data/5e-SRD-Monsters.json'*/ address);
                store.commit('SET_MONSTERS', data);
            } catch (error) {
                console.error(error);
            }
        },
        loadDetails: async (store, name) => {
            const { protocol, host } = location;
            const address = `${protocol}//${host}/api/v1/monsters/details/${name}`;
            try {
                const { data } = await axios.get(address);
                store.commit('SET_MONSTER_DETAILS', data);
            } catch (error) {
                console.error(error);
            }
        }
    },
    mutations: {
        SET_MONSTERS: (state, payload) => {
            state.allMonsters = payload;
        },
        SET_MONSTER_DETAILS: (state, payload) => {
            state.concreteMonster = payload;
        }
    }
};
