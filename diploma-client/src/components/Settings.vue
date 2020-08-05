<template>
    <v-app id="sandbox" :dark="dark">
        <v-content>
            <v-container fluid>
                <v-layout align-center justify-center>
                    <v-flex xs10>
                        <v-card>
                            <v-card-text>
                                <v-layout row wrap>
                                    <v-flex xs12 md6>
                                        <span>Scheme</span>
                                        <v-switch
                                            @click="switchTheme"
                                            v-model="dark"
                                            primary
                                            label="Dark"
                                        />
                                    </v-flex>
                                    <v-flex xs12 md6>
                                        <span>Drawer</span>
                                        <v-radio-group v-model="primaryDrawer.type" column>
                                            <v-radio
                                                v-for="drawer in drawers"
                                                :key="drawer"
                                                :label="drawer"
                                                :value="drawer.toLowerCase()"
                                                primary
                                            />
                                        </v-radio-group>
                                        <v-switch
                                            @switch="switchClippedMode"
                                            v-model="primaryDrawer.clipped"
                                            label="Clipped"
                                            primary
                                        />
                                        <v-switch
                                            @switch="switchFloatingMode"
                                            v-model="primaryDrawer.floating"
                                            label="Floating"
                                            primary
                                        />
                                        <v-switch
                                            @switch="switchMiniMode"
                                            v-model="primaryDrawer.mini"
                                            label="Mini"
                                            primary
                                        />
                                    </v-flex>
                                    <v-flex xs12 md6>
                                        <span>Footer</span>
                                        <v-switch
                                            @switch="switchFooterInsetMode"
                                            v-model="footer.inset"
                                            label="Inset"
                                            primary
                                        />
                                    </v-flex>
                                </v-layout>
                            </v-card-text>
                            <v-card-actions>
                                <v-spacer />
                                <v-btn flat>Cancel</v-btn>
                                <v-btn flat color="primary">Submit</v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
import { mapState } from 'vuex';

export default {
    computed: mapState({
        dark: state => state.application.dark,
        drawers: state => state.application.drawers,
        primaryDrawer: state => state.application.primaryDrawer,
        footer: state => state.application.footer
    }),
    methods: {
        switchTheme() {
            this.$store.dispatch('application/switchTheme');
        },
        switchMiniMode() {
            this.$store.dispatch('application/switchMiniMode');
        },
        switchFloatingMode() {
            this.$store.dispatch('application/switchFloatingMode');
        },
        switchClippedMode() {
            this.$store.dispatch('application/switchClippedMode');
        },
        switchFooterInsetMode() {
            this.$store.dispatch('application/switchFooterInsetMode');
        }
    }
};
</script>
