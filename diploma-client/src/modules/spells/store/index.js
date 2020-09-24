import axios from 'axios';
import { serverApiHost } from '@/config/server-api';

export const action = {
    load: 'load',
    loadDetails: 'loadDetails',
};

const mutation = {
    SET_SPELLS: 'SET_SPELLS',
};

export default {
    namespaced: true,
    state: {
        allSpells: [],
    },
    getters: {},
    actions: {
        [action.load]: async ({ commit }) => {
            try {
                const { data } = await axios.get(`${serverApiHost}/api/v1/spells`);
                commit(mutation.SET_SPELLS, data);
            } catch (e) {
                console.error(e);
            }
        },

        [action.loadDetails]: async ({ commit }, name) => {
            try {
                const { data } = await axios.get(`${serverApiHost}/api/v1/spells/${name}/details`);
                commit(mutation.SET_MONSTER_DETAILS, data);
            } catch (error) {
                console.error(error);
            }
        },
    },
    mutations: {
        [mutation.SET_SPELLS]: (state, payload) => {
            state.allSpells = payload;
        },
    },
};
