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
                <character-info
                    :name="'Arhor'"
                    :str="20"
                    :dex="0"
                    :con="0"
                    :int="0"
                    :wis="0"
                    :cha="0"
                />
            </v-col>
            <v-col cols="8">
                <v-stepper-items>
                    <v-stepper-content
                        v-for="(step, i) in steps"
                        :key="`step-${i + 1}`"
                        :step="i + 1"
                    >
                        <component :is="step.component" @step-complete="handleStepComplete" />
                    </v-stepper-content>
                </v-stepper-items>
            </v-col>
        </v-row>
    </v-stepper>
</template>

<script>
import CharacterInfo from '@/modules/characters/components/CharacterCreator/CharacterInfo.vue';
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
