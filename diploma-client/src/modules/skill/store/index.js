import axios from '@/api/BaseService.js';

const mutation = {
    SET_SKILLS: 'SET_SKILLS',
};

export default {
    namespaced: true,
    state: {
        allSkills: [],
        isModuleLoaded: false,
    },
    getters: {},
    actions: {
        load: async ({ commit, state }) => {
            if (!state.isModuleLoaded) {
                try {
                    const { data } = await axios.get('data/5e-SRD-Skills.json');
                    commit(mutation.SET_SKILLS, data);
                } catch (e) {
                    console.log(e);
                }
            }
        },
    },
    mutations: {
        [mutation.SET_SKILLS]: (state, payload) => {
            state.allSkills = payload;
            state.isModuleLoaded = true;
        },
    },
};
