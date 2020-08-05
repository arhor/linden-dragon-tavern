<template>
    <v-tooltip top>
        <template v-slot:activator="{ on }">
            <v-container class="text-xs-center" color="primary" v-if="ability && value" v-on="on">
                <v-layout row wrap>
                    <v-flex xs12>
                        <strong>{{ ability.name }}</strong>
                    </v-flex>
                    <v-flex xs12> {{ value }} ({{ value | calcBonus }}) </v-flex>
                </v-layout>
            </v-container>
        </template>
        <span>{{ ability.full_name }}</span>
    </v-tooltip>
</template>

<script>
export default {
    name: 'AbilityDetails',
    props: {
        ability: {
            type: Object,
            default: null
        },
        value: {
            type: Number,
            default: 0
        }
    },
    filters: {
        calcBonus(stat) {
            const result = Math.floor((stat - 10) / 2);
            return (result >= 0 ? '+' : '').concat(result);
        }
    }
};
</script>
