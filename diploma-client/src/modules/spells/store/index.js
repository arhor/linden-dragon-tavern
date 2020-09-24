import axios from 'axios';
import { SERVER_API_URL } from '@/config/server-api';

const SPELLS_BASE_URL = `${SERVER_API_URL}/api/v1/spells`;

const mutation = {
    SET_SPELLS: 'SET_SPELLS',
};

export default {
    namespaced: true,
    state: {
        allSpells: [],
        isModuleLoaded: false,
    },
    getters: {},
    actions: {
        load: async ({ commit, state }) => {
            if (!state.isModuleLoaded) {
                try {
                    const { data } = await axios.get(SPELLS_BASE_URL);
                    commit(mutation.SET_SPELLS, data);
                } catch (e) {
                    console.error(e);
                }
            }
        },

        loadDetails: async ({ commit }, name) => {
            try {
                const { data } = await axios.get(`${SPELLS_BASE_URL}/${name}/details`);
                commit(mutation.SET_MONSTER_DETAILS, data);
            } catch (error) {
                console.error(error);
            }
        },
    },
    mutations: {
        [mutation.SET_SPELLS]: (state, payload) => {
            state.allSpells = payload;
            state.isModuleLoaded = true;
        },
    },
};
