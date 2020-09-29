import spellService from '@/modules/spells/services/SpellService.js';

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
                    const spells = await spellService.getAllSpells();
                    commit(mutation.SET_SPELLS, spells);
                } catch (e) {
                    console.error(e);
                }
            }
        },

        loadDetails: async ({ commit }, name) => {
            try {
                const spell = await spellService.getSpellByName(name);
                commit(mutation.SET_MONSTER_DETAILS, spell);
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
