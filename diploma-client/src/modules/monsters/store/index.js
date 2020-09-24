import axios from 'axios';
import { serverApiHost } from '@/config/server-api';

export const action = {
    load: 'load',
    loadDetails: 'loadDetails',
};

const mutation = {
    SET_MONSTERS: 'SET_MONSTERS',
    SET_MONSTER_DETAILS: 'SET_MONSTER_DETAILS',
};

const actions = {
    [action.load]: async ({ commit }) => {
        try {
            const { data } = await axios.get(`${serverApiHost}/api/v1/monsters`);
            commit(mutation.SET_MONSTERS, data);
        } catch (error) {
            console.error(error);
        }
    },

    [action.loadDetails]: async ({ commit }, name) => {
        try {
            const { data } = await axios.get(`${serverApiHost}/api/v1/monsters/${name}/details`);
            commit(mutation.SET_MONSTER_DETAILS, data);
        } catch (error) {
            console.error(error);
        }
    },
};

const mutations = {
    [mutation.SET_MONSTERS]: (state, payload) => {
        state.allMonsters = payload;
    },
    [mutation.SET_MONSTER_DETAILS]: (state, payload) => {
        state.currentMonster = payload;
    },
};

export default {
    namespaced: true,
    state: {
        allMonsters: [],
        currentMonster: null,
    },
    getters: {},
    actions,
    mutations,
};
