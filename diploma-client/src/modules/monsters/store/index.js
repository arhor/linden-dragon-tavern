import axios from '@/api/BaseService.js';

const MONSTERS_BASE_URL = `/api/v1/monsters`;

const mutation = {
    SET_MONSTERS: 'SET_MONSTERS',
    SET_MONSTER_DETAILS: 'SET_MONSTER_DETAILS',
    CACHE_MONSTER_DETAILS: 'CACHE_MONSTER_DETAILS',
};

export default {
    namespaced: true,
    state: {
        items: [],
        total: 0,
        monsterDetailsCache: [],
        currentMonster: null,
    },
    getters: {
        getMonsterDetailsFromCache: (state) => (name) => {
            return state.monsterDetailsCache.find((monster) => monster.name === name);
        },
    },
    actions: {
        loadMonstersPage: async ({ commit }, { page, itemsPerPage, sortBy, sortDesc, search }) => {
            try {
                const query = new URLSearchParams();

                query.set('page', page);
                query.set('size', itemsPerPage);

                if (sortBy?.length > 0) {
                    query.set('sortBy', sortBy);
                }
                if (sortDesc?.length > 0) {
                    query.set('sortDesc', sortDesc);
                }
                if (search?.length > 0) {
                    query.set('search', search);
                }

                const { data } = await axios.get(`${MONSTERS_BASE_URL}?${query.toString()}`);
                commit(mutation.SET_MONSTERS, data);
            } catch (e) {
                console.error('Failed attempt to fetch monsters list', e);
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
        [mutation.SET_MONSTERS]: (state, { items, total }) => {
            state.items = items;
            state.total = total;
        },
        [mutation.SET_MONSTER_DETAILS]: (state, monster) => {
            if (state.currentMonster?.name !== monster?.name) {
                state.currentMonster = monster;
            }
        },
        [mutation.CACHE_MONSTER_DETAILS]: (state, monster) => {
            const isAlreadyCached = state.monsterDetailsCache
                .map((it) => it.name)
                .includes(monster.name);

            if (!isAlreadyCached) {
                state.monsterDetailsCache.push(monster);
            }
        },
    },
};
