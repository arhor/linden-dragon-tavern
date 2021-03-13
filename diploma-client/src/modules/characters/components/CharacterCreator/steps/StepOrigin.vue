<template>
    <v-card color="grey lighten-1" height="400px" tile>
        <v-card-text>
            <form>
                <v-text-field v-model="origin.name" label="Name" @change="emitDataChanges" />
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
import { Origin } from '@/modules/characters/components/CharacterCreator/model';

export default {
    name: 'StepOrigin',

    extends: Step,

    data: () => ({
        background: '',
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
        origin: new Origin(),
    }),

    computed: {
        proficiencies() {
            return this.backgrounds.find((it) => it.title === this.background)?.proficiencies ?? [];
        },

        stepData() {
            return {
                origin: this.origin.clone({
                    proficiencies: this.proficiencies,
                }),
            };
        },
    },
};
</script>
