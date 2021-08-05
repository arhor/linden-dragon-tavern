<template>
    <v-card color="grey lighten-1" height="400px" tile>
        <v-card-text>
            <form>
                <v-select
                    v-model="race"
                    :items="races"
                    label="Race"
                    required
                    @change="emitDataChanges"
                />
                <v-select
                    v-model="subRace"
                    :items="subRaces"
                    label="SubRace"
                    required
                    @change="emitDataChanges"
                />
                <v-select
                    v-model="characterClass"
                    :items="classes"
                    label="Class"
                    required
                    @change="emitDataChanges"
                />
                <v-select
                    v-model="background"
                    :items="backgrounds.map((it) => it.title)"
                    label="Background"
                    required
                    @change="emitDataChanges"
                />
            </form>
        </v-card-text>
    </v-card>
</template>

<script>
import Step from '@/modules/characters/components/CharacterCreator/steps/Step';

export default {
    name: 'StepOrigin',

    extends: Step,

    // TODO map vuex state
    data: () => ({
        background: '',
        characterClass: '',
        race: '',
        subRace: '',
    }),

    computed: {
        backgrounds() {
            return this.$store.state.characterStats.backgrounds;
        },

        classes() {
            return this.$store.getters['characterStats/classNames'];
        },

        races() {
            return this.$store.getters['characterStats/raceNames'];
        },

        subRaces() {
            return this.$store.getters['characterStats/subRaces'](this.race);
        },

        stepData() {
            return {
                background: this.background,
                characterClass: this.characterClass,
                race: this.race,
                subRace: this.subRace,
            };
        },

        watch: {
            characterClass() {
                this.$store.dispatch('characterStats/updateCharacterStats', {
                    pickedSkills: [],
                });
            },
        },
    },
};
</script>
