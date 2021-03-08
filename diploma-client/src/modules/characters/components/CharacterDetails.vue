<template>
    <v-card v-if="character" xs10 md10>
        <v-card-title primary-title>
            <span class="title font-weight-light">Character Details...</span>
        </v-card-title>

        <v-card-text class="headline font-weight-bold">
            work in progress =)
        </v-card-text>
    </v-card>
</template>

<script>
export default {
    name: 'CharacterDetails',
    props: {
        character: {
            type: Object,
            default: null,
        },
    },
};
</script>
