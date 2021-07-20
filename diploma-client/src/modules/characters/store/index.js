import axios from 'axios';

const mutation = {
    SET_CLASSES: 'SET_CLASSES',
    SET_RACES: 'SET_RACES',
    SET_ABILITIES: 'SET_ABILITIES',
};

const abilityModes = {
    SIMPLE: 'SIMPLE',
};

const ABILITY_DEFAULT_VALUE = 10;

export default {
    namespaced: true,
    state: {
        allClasses: [],
        allRaces: [],
        abilities: [
            { name: 'Strength', value: ABILITY_DEFAULT_VALUE },
            { name: 'Dexterity', value: ABILITY_DEFAULT_VALUE },
            { name: 'Constitution', value: ABILITY_DEFAULT_VALUE },
            { name: 'Intelligence', value: ABILITY_DEFAULT_VALUE },
            { name: 'Wisdom', value: ABILITY_DEFAULT_VALUE },
            { name: 'Charisma', value: ABILITY_DEFAULT_VALUE },
        ],
        simpleModeMap: {
            0: 15,
            1: 14,
            2: 13,
            3: 12,
            4: 10,
            5: 8,
        },
        abilityMode: abilityModes.SIMPLE,
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
        updateAbilities: ({ commit, state }, value) => {
            const currentAbilityMode = state.abilityMode;
            let newValue = [];
            if (currentAbilityMode === abilityModes.SIMPLE) {
                newValue = value.map((ability, index) => {
                    const simpleModeValue = state.simpleModeMap[index];
                    return { ...ability, value: simpleModeValue };
                });
            }

            commit(mutation.SET_ABILITIES, newValue);
        },
    },
    mutations: {
        [mutation.SET_CLASSES]: (state, payload) => {
            state.allClasses = payload;
        },
        [mutation.SET_RACES]: (state, payload) => {
            state.allRaces = payload;
        },
        [mutation.SET_ABILITIES]: (state, payload) => {
            state.abilities = payload;
        },
    },
};
