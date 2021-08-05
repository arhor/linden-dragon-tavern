<template>
    <div>
        <v-card color="grey lighten-1" height="400px" tile>
            <v-list shaped>
                <v-list-item-group
                    v-model="pickedRaceAbilities"
                    :max="raceAbilityBonusOptions.limit"
                    multiple
                >
                    <template v-for="{ name } in raceAbilityBonusOptions.abilities">
                        <v-list-item
                            :key="name"
                            :value="name"
                            active-class="deep-purple--text text--accent-4"
                        >
                            <template v-slot:default="{ active }">
                                <v-list-item-content>
                                    <v-list-item-title v-text="name"></v-list-item-title>
                                </v-list-item-content>

                                <v-list-item-action>
                                    <v-checkbox
                                        disabled
                                        :input-value="active"
                                        color="deep-purple accent-4"
                                    ></v-checkbox>
                                </v-list-item-action>
                            </template>
                        </v-list-item>
                    </template>
                </v-list-item-group>
            </v-list>
            <draggable v-model="abilities">
                <v-chip
                    v-for="({ name, value }, i) in abilities"
                    :key="`${name}${value}${i}`"
                    class="ma-2"
                    color="green"
                    text-color="white"
                >
                    <v-avatar left class="green darken-4">
                        {{ value }}
                    </v-avatar>
                    {{ name }}
                </v-chip>
            </draggable>
        </v-card>
    </div>
</template>

<script>
import Step from '@/modules/characters/components/CharacterCreator/steps/Step';
import draggable from 'vuedraggable';

export default {
    name: 'StepRace',

    extends: Step,

    components: {
        draggable,
    },

    data: () => ({}),

    computed: {
        raceAbilityBonusOptions() {
            return this.$store.getters['characterStats/raceAbilityBonusOptions'];
        },

        pickedRaceAbilities: {
            get() {
                //TODO set initial value
                return this.$store.state.characterStats.pickedRaceAbilities;
            },
            set(value) {
                this.$store.dispatch('characterStats/updateCharacterStats', {
                    pickedRaceAbilities: value,
                });
            },
        },

        abilities: {
            get() {
                return this.$store.state.characterStats.abilities;
            },
            set(value) {
                this.$store.dispatch('characterStats/updateAbilities', value);
            },
        },
    },
};
</script>
