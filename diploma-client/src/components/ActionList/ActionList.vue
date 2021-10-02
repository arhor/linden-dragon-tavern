<template>
    <v-row>
        <v-col v-for="({ name, desc }, i) in actions" :key="name" cols="12">
            <strong>{{ name }}</strong
            >: {{ desc }}{{ i < actions.length - 1 ? ', ' : '' }}
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
};
</script>
