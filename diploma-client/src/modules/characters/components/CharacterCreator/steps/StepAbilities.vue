<template>
    <div>
        <v-card class="mb-12" color="grey lighten-1" height="325px"></v-card>
        <v-btn color="primary" @click="$emit('step-complete')">Continue</v-btn>
        <v-btn text>Cancel</v-btn>
    </div>
</template>

<script>
export default {
    name: 'StepAbilities',
    data: () => ({}),
};
</script>
