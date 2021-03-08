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

        <v-stepper-items>
            <v-stepper-content v-for="(step, i) in steps" :key="`step-${i + 1}`" :step="i + 1">
                <component :is="step.component" @step-complete="handleStepComplete" />
            </v-stepper-content>
        </v-stepper-items>
    </v-stepper>
</template>

<script>
import {
    StepAbilities,
    StepAppearance,
    StepClass,
    StepOrigin,
    StepRace,
    StepSkills,
} from '@/modules/characters/components/CharacterCreator/steps';

export default {
    name: 'CharacterCreator',
    data: () => ({
        steps: [
            { title: 'Origin', component: StepOrigin },
            { title: 'Race', component: StepRace },
            { title: 'Appearance', component: StepAppearance },
            { title: 'Class', component: StepClass },
            { title: 'Skills', component: StepSkills },
            { title: 'Abilities', component: StepAbilities },
        ],
        currentStep: 1,
    }),
    methods: {
        handleStepComplete() {
            // temp-behavior: loop should be removed after implementation complete
            if (this.currentStep === this.steps.length) {
                this.currentStep = 1;
            } else {
                this.currentStep++;
            }
        },
    },
};
</script>
