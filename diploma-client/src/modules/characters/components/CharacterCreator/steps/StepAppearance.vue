<template>
    <div>
        <v-card class="mb-12" color="grey lighten-1" height="200px"></v-card>
        <v-btn color="primary" @click="$emit('step-complete')">Continue</v-btn>
        <v-btn text>Cancel</v-btn>
    </div>
</template>

<script>
export default {
    name: 'StepAppearance',
    data: () => ({}),
};
</script>
