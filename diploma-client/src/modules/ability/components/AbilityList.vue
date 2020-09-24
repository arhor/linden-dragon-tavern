<template>
    <v-row wrap>
        <v-col v-for="ability in abilities" :key="ability.name" cols="2">
            <ability-details :ability="ability" :value="valueOf(ability)" />
        </v-col>
    </v-row>
</template>

<script>
import { mapState } from 'vuex';
import AbilityDetails from '@/modules/ability/components/AbilityDetails.vue';

export default {
    name: 'AbilityList',
    props: {
        creature: {
            type: Object,
            default: null,
        },
    },
    components: {
        AbilityDetails,
    },
    computed: mapState({
        abilities: (state) => state.abilities.all,
    }),
    methods: {
        valueOf(ability) {
            return this.creature.abilities[ability.name];
        },
    },
};
</script>
