<template>
    <v-stepper v-model="currentStep" non-linear>
        <v-stepper-header>
            <template v-for="(step, i) in steps">
                <v-stepper-step :key="`step-header-${i}`" :step="i + 1" editable>
                    {{ step.title }}
                </v-stepper-step>
                <v-divider :key="`step-separator-${i}`" v-if="i < steps.length - 1" />
            </template>
        </v-stepper-header>

        <v-row align="center" justify="center" align-content="center">
            <v-col cols="4">
                <character-info v-bind="character" />
            </v-col>
            <v-col cols="8">
                <v-stepper-items>
                    <v-stepper-content
                        v-for="(step, i) in steps"
                        :key="`step-${i + 1}`"
                        :step="i + 1"
                    >
                        <component :is="step.component" @data-changed="handleChanges" />
                    </v-stepper-content>
                </v-stepper-items>
            </v-col>
        </v-row>
    </v-stepper>
</template>

<script>
import CharacterInfo from '@/modules/characters/components/CharacterCreator/CharacterInfo.vue';
import {
    Abilities,
    Class,
    Origin,
    Race,
    Skills,
} from '@/modules/characters/components/CharacterCreator/model';
import {
    StepAbilities,
    StepClass,
    StepOrigin,
    StepRace,
    StepSkills,
} from '@/modules/characters/components/CharacterCreator/steps';

export default {
    name: 'CharacterCreator',

    components: { CharacterInfo },

    data: () => ({
        steps: [
            { title: 'Origin', component: StepOrigin },
            { title: 'Race', component: StepRace },
            { title: 'Class', component: StepClass },
            { title: 'Skills', component: StepSkills },
            { title: 'Abilities', component: StepAbilities },
        ],
        currentStep: 1,
        character: {
            abilities: new Abilities(),
            class: new Class(),
            origin: new Origin(),
            race: new Race(),
            skills: new Skills(),
        },
    }),

    methods: {
        handleChanges(changes) {
            this.character = {
                ...this.character,
                ...changes,
            };
        },
    },
};
</script>
