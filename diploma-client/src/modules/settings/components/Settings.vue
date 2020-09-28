<template>
    <v-card>
        <v-card-text>
            <v-row row wrap>
                <v-col cols="12">
                    <span>Scheme</span>
                    <v-switch @click="switchTheme" v-model="dark" primary label="Dark" />
                </v-col>
            </v-row>
        </v-card-text>
        <v-card-actions>
            <v-spacer />
            <v-btn>Cancel</v-btn>
            <v-btn color="primary">Submit</v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
import { mapActions, mapState } from 'vuex';

export default {
    data: () => ({
        ...mapState(['dark']),
    }),
    methods: {
        ...mapActions(['switchTheme']),
    },
};
</script>
