<template>
    <v-card>
        <v-card-text>
            <v-row wrap>
                <v-col cols="12">
                    <span>Scheme</span>
                    <v-switch @click="switchTheme" v-model="dark" primary label="Dark" />
                </v-col>
            </v-row>

            <v-row>
                <v-menu rounded="lg" offset-y>
                    <template v-slot:activator="{ attrs, on }">
                        <v-btn color="deep-purple accent-4" class="white--text ma-5" v-bind="attrs" v-on="on">
                            language
                        </v-btn>
                    </template>

                    <v-list>
                        <v-list-item v-for="item in items" :key="item" link @click="switchLang(item)">
                            <v-list-item-title v-text="item" />
                        </v-list-item>
                    </v-list>
                </v-menu>
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

import { loadLanguageAsync } from '@/plugins/i18n';

export default {
    name: 'DndAppSettings',
    data: () => ({
        ...mapState(['dark']),
        items: ['EN', 'RU'],
    }),
    methods: {
        ...mapActions(['switchTheme']),
        switchLang(lang) {
            loadLanguageAsync(lang.toLowerCase());
        },
    },
};
</script>
