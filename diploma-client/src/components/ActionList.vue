<template functional>
    <v-row>
        <v-col v-for="{ name, desc } in props.actions" :key="name" cols="12">
            <strong>{{ name }}</strong>
            : {{ desc }}
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
