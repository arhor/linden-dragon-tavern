import axios from 'axios';

const mutation = {
    SET_CLASSES: 'SET_CLASSES',
    SET_RACES: 'SET_RACES',
    SET_ABILITIES: 'SET_ABILITIES',
    SET_CHARACTER_STATS: 'SET_CHARACTER_STATS',
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
        backgrounds: [
            { title: 'Acolyte', proficiencies: ['Insight', 'Religion'] },
            { title: 'Charlatan', proficiencies: ['Deception', 'Sleight of Hand'] },
            { title: 'Criminal', proficiencies: ['Deception', 'Stealth'] },
            { title: 'Entertainer', proficiencies: ['Acrobatics', 'Performance'] },
            { title: 'Folk Hero', proficiencies: ['Animal Handling', 'Survival'] },
            { title: 'Guild Artisan', proficiencies: ['Insight', 'Persuasion'] },
            { title: 'Noble', proficiencies: ['History', 'Persuasion'] },
            { title: 'Hermit', proficiencies: ['Medicine', 'Religion'] },
            { title: 'Outlander', proficiencies: ['Athletics', 'Survival'] },
            { title: 'Sage', proficiencies: ['Arcana', 'History'] },
            { title: 'Sailor', proficiencies: ['Athletics', 'Perception'] },
            { title: 'Soldier', proficiencies: ['Athletics', 'Intimidation'] },
            { title: 'Urchin', proficiencies: ['Sleight of Hand', 'Stealth'] },
        ],
        simpleModeMap: {
            0: 15,
            1: 14,
            2: 13,
            3: 12,
            4: 10,
            5: 8,
        },
        // TODO implement Character model
        // character: new Character()
        character: {},
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
            //TODO calc race name from state
            const currentRace = state.allRaces.find(({ name }) => name === race);
            return currentRace?.subraces.map(({ name }) => name).concat(['no subrace']) || [];
        },

        classSkills: (state) => {
            const currentClass = state.allClasses.find(
                ({ name }) => name === state.character.characterClass,
            );
            const skills = currentClass?.proficiency_choices?.find(({ type }) => type === 'skill');

            return {
                skills: skills?.from || [],
                limit: skills?.choose || 0,
            };
        },

        // TODO race dependance reset on change
        raceAbilityBonusOptions: (state) => {
            const currentRaceName = state.character.race;
            const currentRace = state.allRaces.find(({ name }) => name === currentRaceName);
            return {
                abilities: currentRace?.ability_bonus_options?.from || [],
                limit: currentRace?.ability_bonus_options?.choose || 0,
            };
        },

        raceAbilityBonuses: (state) => {
            const currentRaceName = state.character.race;
            const currentRace = state.allRaces.find(({ name }) => name === currentRaceName);
            return currentRace?.ability_bonuses || [];
        },

        backgroundProficiencies(state) {
            return (
                state.backgrounds.find(({ title }) => title === state.character.background)
                    ?.proficiencies || []
            );
        },
    },
    actions: {
        load: async ({ commit, state }) => {
            if (!state.isStatsLoaded) {
                try {
                    //TODO use api service
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

        updateCharacterStats: ({ commit, state }, changes) => {
            const stats = {
                ...state.character,
                ...changes,
            };
            commit(mutation.SET_CHARACTER_STATS, stats);
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
        [mutation.SET_CHARACTER_STATS]: (state, payload) => {
            state.character = payload;
        },
    },
};
