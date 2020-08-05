<template>
    <v-row>
        <v-col v-for="(action, i) in actions" :key="`action-${i}`" cols="12">
            <strong>{{ action.name }}</strong>
            : {{ action.desc }}
        </v-col>
    </v-row>
</template>

<script>
export default {
    name: 'ActionList',
    props: {
        actions: {
            type: Array,
            default: () => []
        }
    }
};
</script>
