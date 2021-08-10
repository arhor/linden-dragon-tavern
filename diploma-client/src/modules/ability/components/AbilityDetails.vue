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
        return {
            modifier: computed(() => {
                const modifier = int(int(int(props.value) - 10) / 2);
                return `${modifier < 0 ? '-' : '+'}${modifier}`;
            }),
        };
    },
};
</script>
