<template>
    <div>
        <v-card color="grey lighten-1" height="400px" tile>
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

    data: () => ({
        selectedItem: 1,
    }),

    computed: {
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
