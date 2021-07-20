import axios from 'axios';

const mutation = {
    SET_CLASSES: 'SET_CLASSES',
    SET_RACES: 'SET_RACES',
};

export default {
    namespaced: true,
    state: {
        allClasses: [],
        allRaces: [],
        isStatsLoaded: false,
    },
    getters: {
        classNames: (state) => {
            return state.allClasses.map(({ name }) => name) || [];
        },
        raceNames: (state) => {
            return state.allRaces.map(({ name }) => name) || [];
        },
        subRaces: (state) => (race) => {
            const currentRace = state.allRaces.find(({ name }) => name === race);
            return currentRace?.subraces.map(({ name }) => name).concat(['no subrace']) || [];
        },
    },
    actions: {
        load: async ({ commit, state }) => {
            if (!state.isStatsLoaded) {
                try {
                    const classesPromise = axios.get('data/5e-SRD-Classes.json');
                    const racesPromise = axios.get('data/5e-SRD-Races.json');
                    const [classes, races] = await Promise.all([classesPromise, racesPromise]);
                    commit(mutation.SET_CLASSES, classes.data);
                    commit(mutation.SET_RACES, races.data);
                    state.isStatsLoaded = true;
                } catch (e) {
                    console.error(e);
                }
            }
        },
    },
    mutations: {
        [mutation.SET_CLASSES]: (state, payload) => {
            state.allClasses = payload;
        },
        [mutation.SET_RACES]: (state, payload) => {
            state.allRaces = payload;
        },
    },
};
