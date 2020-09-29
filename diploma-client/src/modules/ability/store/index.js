import axios from '@/api/BaseService.js';

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
                    const { data } = await axios.get('data/5e-SRD-Ability-Scores.json');
                    commit(mutation.SET_ABILITIES, data);
                } catch (e) {
                    console.error(e);
                }
            }
        },
    },
    mutations: {
        [mutation.SET_ABILITIES]: (state, payload) => {
            state.all = payload;
            state.isAbilitiesLoaded = true;
        },
    },
};
