<template>
    <v-card color="grey lighten-1" height="400px" tile>
        <v-list shaped>
            <v-list-item-group v-model="pickedSkills" :max="classSkills.limit" multiple>
                <template v-for="({ name }, i) in classSkills.skills">
                    <v-list-item
                        :key="`${name}-${i}`"
                        :value="name"
                        :disabled="backgroundProficiencies.includes(name)"
                        active-class="deep-purple--text text--accent-4"
                    >
                        <template v-slot:default="{ active }">
                            <v-list-item-content>
                                <v-list-item-title v-text="name"></v-list-item-title>
                            </v-list-item-content>

                            <v-list-item-action>
                                <v-checkbox
                                    disabled
                                    :input-value="active || backgroundProficiencies.includes(name)"
                                    color="deep-purple accent-4"
                                ></v-checkbox>
                            </v-list-item-action>
                        </template>
                    </v-list-item>
                </template>
            </v-list-item-group>
        </v-list>
    </v-card>
</template>

<script>
import Step from '@/views/characters/components/CharacterCreator/steps/Step';

export default {
    name: 'StepClass',

    extends: Step,

    data: () => ({}),

    computed: {
        backgroundProficiencies() {
            return this.$store.getters['characterStats/backgroundProficiencies'];
        },

        classSkills() {
            return this.$store.getters['characterStats/classSkills'];
        },

        pickedSkills: {
            get() {
                return this.$store.state.characterStats.pickedSkills;
            },
            set(value) {
                this.$store.dispatch('characterStats/updateCharacterStats', {
                    pickedSkills: value,
                });
            },
        },
    },

    watch: {},
};
</script>
