<template>
    <v-card class="mx-6 mt-5 elevation-0" color="grey lighten-1" height="400px" tile>
        <v-row justify="space-around" class="py-3">
            <span class="white--text">{{ $t('name') }}: {{ origin.name }}</span>
        </v-row>

        <v-row
            v-for="(abilityRow, i) in abilityRows"
            :key="`ability-row-${i}`"
            justify="space-around"
            class="py-5 px-16"
        >
            <ability-point
                v-for="(ability, j) in abilityRow"
                :key="`ability-${j}`"
                :title="$t(ability.title).toUpperCase()"
                :value="ability.value"
            />
        </v-row>
    </v-card>
</template>

<script>
import {computed} from '@vue/composition-api';

import AbilityPoint from '@/views/characters/components/CharacterCreator/AbilityPoint';
import {Abilities, Class, Origin, Race, Skills,} from '@/views/characters/components/CharacterCreator/model';

export default {
    name: 'CharacterInfo',

    components: { AbilityPoint },

    props: {
        origin: { type: Origin },
        race: { type: Race },
        class: { type: Class },
        skills: { type: Skills },
        abilities: { type: Abilities },
    },

    setup(props) {
        const abilityRows = computed(() => [
            [
                { title: 'str', value: props.abilities.str },
                { title: 'dex', value: props.abilities.dex },
                { title: 'con', value: props.abilities.con },
            ],
            [
                { title: 'int', value: props.abilities.int },
                { title: 'wis', value: props.abilities.wis },
                { title: 'cha', value: props.abilities.cha },
            ],
        ]);

        return { abilityRows };
    },
};
</script>
