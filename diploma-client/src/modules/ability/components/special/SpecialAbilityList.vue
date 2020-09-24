<template>
    <v-row v-if="notEmpty">
        <v-col v-for="{ name, desc } in specialAbilities" :key="name" cols="12" class="text-left">
            <strong>{{ name }}</strong>
            :
            <span v-html="renderLinebreaksHTML(desc)"></span>
        </v-col>
    </v-row>
</template>

<script>
import { renderLinebreaksHTML } from '@/utils/stringUtils';
import { notEmptyArray } from '@/utils/arrayUtils';

export default {
    name: 'SpecialAbilityList',
    props: {
        specialAbilities: {
            type: Array,
            default: () => [],
        },
    },
    computed: {
        notEmpty() {
            return notEmptyArray(this.specialAbilities);
        },
    },
    methods: {
        renderLinebreaksHTML,
    },
};
</script>
