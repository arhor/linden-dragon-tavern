import axios from 'axios';
import { SERVER_API_URL } from '@/config/server-api.js';

const MONSTERS_BASE_URL = `${SERVER_API_URL}/api/v1/monsters`;

const mutation = {
    SET_MONSTERS: 'SET_MONSTERS',
    SET_MONSTER_DETAILS: 'SET_MONSTER_DETAILS',
    CACHE_MONSTER_DETAILS: 'CACHE_MONSTER_DETAILS',
};

export default {
    namespaced: true,
    state: {
        allMonsters: [],
        monsterDetailsCache: [],
        currentMonster: null,
        isModuleLoaded: false,
    },
    getters: {
        getMonsterDetailsFromCache: (state) => (name) => {
            return state.monsterDetailsCache.find((monster) => monster.name === name);
        },
    },
    actions: {
        load: async ({ commit, state }) => {
            if (!state.isModuleLoaded) {
                try {
                    const { data } = await axios.get(MONSTERS_BASE_URL);
                    commit(mutation.SET_MONSTERS, data);
                } catch (error) {
                    console.error(error);
                }
            }
        },

        loadDetails: async ({ commit, getters: { getMonsterDetailsFromCache } }, name) => {
            const cachedDetails = getMonsterDetailsFromCache(name);

            if (cachedDetails) {
                commit(mutation.SET_MONSTER_DETAILS, cachedDetails);
                return;
            }

            try {
                const { data } = await axios.get(`${MONSTERS_BASE_URL}/${name}/details`);
                commit(mutation.CACHE_MONSTER_DETAILS, data);
                commit(mutation.SET_MONSTER_DETAILS, data);
            } catch (error) {
                console.error(error);
            }
        },
    },
    mutations: {
        [mutation.SET_MONSTERS]: (state, payload) => {
            state.allMonsters = payload;
            state.isModuleLoaded = true;
        },
        [mutation.SET_MONSTER_DETAILS]: (state, payload) => {
            if (state.currentMonster?.name !== payload?.name) {
                state.currentMonster = payload;
            }
        },
        [mutation.CACHE_MONSTER_DETAILS]: (state, payload) => {
            const isAlreadyCached = state.monsterDetailsCache
                .map((monster) => monster.name)
                .includes(payload.name);

            if (!isAlreadyCached) {
                state.monsterDetailsCache.push(payload);
            }
        },
    },
};
