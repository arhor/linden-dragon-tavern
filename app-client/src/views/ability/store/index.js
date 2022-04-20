import axios from 'axios';

const API_URL = '/api/v1/abilities';
const API_FALLBACK_URL = 'data/5e-SRD-Ability-Scores.json';

const mutation = {
    SET_ABILITIES: 'SET_ABILITIES',
};

export default {
    namespaced: true,
    state: {
        allAbilities: [],
        isAbilitiesLoaded: false,
    },
    getters: {},
    actions: {
        load: async ({ commit, state }) => {
            if (!state.isAbilitiesLoaded) {
                try {
                    const { data } = await axios.get(API_URL);
                    commit(mutation.SET_ABILITIES, data.items);
                } catch (apiError) {
                    console.error('Unable to load abilities list.', apiError);
                    if (process.env.NODE_ENV === 'development') {
                        console.debug('Trying to use fallback URL...');
                        try {
                            const { data } = await axios.get(API_FALLBACK_URL);
                            commit(mutation.SET_ABILITIES, data);
                            console.debug('Success.');
                        } catch (fallbackError) {
                            console.error('Loading abilities list failed.', fallbackError);
                        }
                    }
                }
            }
        },
    },
    mutations: {
        [mutation.SET_ABILITIES]: (state, payload) => {
            state.allAbilities = payload;
            state.isAbilitiesLoaded = true;
        },
    },
};
