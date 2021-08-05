<template>
    <div>
        <v-card color="grey lighten-1" height="400px" tile></v-card>
    </div>
</template>

<script>
import Step from '@/modules/characters/components/CharacterCreator/steps/Step';
import { Abilities } from '@/modules/characters/components/CharacterCreator/model';

export default {
    name: 'StepAbilities',

    extends: Step,

    data: () => ({
        abilities: new Abilities(),
    }),

    computed: {
        stepData() {
            return {
                abilities: this.abilities.clone(),
            };
        },
    },
};
</script>
