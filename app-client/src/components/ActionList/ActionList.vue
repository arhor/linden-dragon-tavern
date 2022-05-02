<template>
    <v-row>
        <v-col v-for="({ name, desc }, i) in actions" :key="name" cols="12">
            <b>{{ name }}</b>{{ `: ${desc}${isLastAction(i) ? ', ' : ''}` }}
        </v-col>
    </v-row>
</template>

<script>
export default {
    name: 'ActionList',

    props: {
        actions: {
            type: Array,
            default: () => [],
        },
    },

    methods: {
        isLastAction(index) {
            return index < this.actions.length - 1;
        },
    },
};
</script>
