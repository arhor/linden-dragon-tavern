<template>
    <v-tooltip top>
        <template v-slot:activator="{ on }">
            <v-container class="text-xs-center" color="primary" v-if="ability && value" v-on="on">
                <v-row>
                    <v-col cols="12">
                        <strong>{{ ability.name }}</strong>
                    </v-col>
                    <v-col cols="12"> {{ value }} ({{ modifier }}) </v-col>
                </v-row>
            </v-container>
        </template>
        <span>{{ ability.fullName }}</span>
    </v-tooltip>
</template>

<script>
import { computed } from '@vue/composition-api';
import { int } from '@/utils/coreUtils';

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
    setup(props) {
        const calcAbilityModifier = (value) => {
            const modifier = int((int(int(value) - 10)) / 2);
            return `${modifier < 0 ? '-' : '+'}${modifier}`;
        };

        return {
            modifier: computed(() => calcAbilityModifier(props.value)),
        };
    },
};
</script>
