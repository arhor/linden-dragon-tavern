<template>
    <v-avatar color="secondary" size="60">
        <span class="white--text">
            {{ title }}
            <br />
            {{ value }}
        </span>
    </v-avatar>
</template>

<script>
export default {
    name: 'AbilityPoint',

    props: {
        title: { type: String, required: true },
        value: { type: Number, required: true },
    },
};
</script>
