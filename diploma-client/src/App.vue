<template>
    <v-app :dark="dark">
        <v-navigation-drawer
            v-model="displayDrawer"
            mini-variant
            temporary
            absolute
            overflow
            :dark="dark"
            app
        >
            <v-list nav dense>
                <v-list-item link @click.stop="displaySettingsPopup">
                    <v-list-item-icon>
                        <v-icon>mdi-cog</v-icon>
                    </v-list-item-icon>
                    <v-list-item-title>DndAppSettings</v-list-item-title>
                </v-list-item>
            </v-list>
            <v-dialog v-model="displaySettings" max-width="800">
                <dnd-app-settings />
            </v-dialog>
        </v-navigation-drawer>

        <v-app-bar :dark="dark" app>
            <v-app-bar-nav-icon @click.stop="displayDrawer = !displayDrawer" />
            <v-toolbar-title>{{ appName }}</v-toolbar-title>
            <v-spacer />
            <v-btn v-for="{ path, name } in appBarLinks" :key="name" :to="path" text>
                {{ name }}
            </v-btn>
            <dnd-download-button :url="characterSheetUrl" />

            <v-btn to="/account" fab dark>
                <v-icon>mdi-account-circle</v-icon>
            </v-btn>
        </v-app-bar>

        <v-main>
            <dnd-breadcrumbs />
            <v-container fluid>
                <router-view />
            </v-container>
        </v-main>

        <v-footer :dark="dark" app>
            <span class="px-3">&copy; {{ new Date().getFullYear() }}</span>
        </v-footer>
    </v-app>
</template>

<script>
import { mapState } from 'vuex';

import DndAppSettings from '@/components/DndAppSettings.vue';
import DndBreadcrumbs from '@/components/DndBreadcrumbs.vue';
import DndDownloadButton from '@/components/DndDownloadButton.vue';
import { SERVER_API_URL } from '@/api/server-api.js';

const MODULES_TO_LOAD = ['abilities', 'spells', 'skills'];

export default {
    name: 'App',
    components: { DndDownloadButton, DndBreadcrumbs, DndAppSettings },
    data: () => ({
        appName: 'D&D Homebrew App',
        displayDrawer: false,
        displaySettings: false,
        appBarLinks: [
            { path: '/', name: 'Home' },
            { path: '/about', name: 'About' },
        ],
    }),
    computed: {
        ...mapState(['dark']),
        characterSheetUrl() {
            return `${SERVER_API_URL}/api/v1/charsheets`;
        },
    },
    methods: {
        displaySettingsPopup() {
            this.displaySettings = true;
        },
    },
    mounted() {
        MODULES_TO_LOAD.forEach((asset) => void this.$store.dispatch(`${asset}/load`));
    },
};
</script>
