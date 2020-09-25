<template>
    <v-tooltip top>
        <template v-slot:activator="{ on }">
            <v-container class="text-xs-center" color="primary" v-if="ability && value" v-on="on">
                <v-row>
                    <v-col cols="12">
                        <strong>{{ ability.name }}</strong>
                    </v-col>
                    <v-col cols="12"> {{ value }} ({{ value | calcBonus }}) </v-col>
                </v-row>
            </v-container>
        </template>
        <span>{{ ability.fullName }}</span>
    </v-tooltip>
</template>

<script>
import sharedLib from '@/lib/diploma-shared';

const { Utils } = sharedLib.org.arhor.diploma;

export default {
    name: 'AbilityDetails',
    props: {
        ability: {
            type: Object,
            default: null,
        },
        value: {
            type: Number,
            default: 0,
        },
    },
    filters: {
        calcBonus: (value) => Utils.calcAbilityModifier(value),
    },
};
</script>
