<template>
    <v-app :dark="dark">
        <v-navigation-drawer v-model="displayDrawer" mini-variant temporary absolute overflow :dark="dark" app>
            <v-list nav dense>
                <v-list-item link @click.stop="displaySettingsPopup">
                    <v-list-item-icon>
                        <v-icon>mdi-cog</v-icon>
                    </v-list-item-icon>
                    <v-list-item-title>DndAppSettings</v-list-item-title>
                </v-list-item>
                <v-list-item link to="/about">
                    <v-list-item-icon>
                        <v-icon>mdi-information-outline</v-icon>
                    </v-list-item-icon>
                    <v-list-item-title>DndAppSettings</v-list-item-title>
                </v-list-item>
                <DndDownloadButton url="/api/charsheets" />
            </v-list>
            <v-dialog v-model="displaySettings" max-width="800">
                <DndAppSettings />
            </v-dialog>
        </v-navigation-drawer>

        <v-app-bar :dark="dark" app>
            <v-app-bar-nav-icon @click.stop="displayDrawer = !displayDrawer" />
            <v-toolbar-title style="cursor: pointer" @click="$router.push('/')">
                {{ appName }}
            </v-toolbar-title>

            <v-chip v-if="authenticated" class="ma-5">Logged in as: {{ username }}</v-chip>

            <v-spacer />

            <v-btn v-if="!authenticated" @click="dialog = true" dark>
                Login
            </v-btn>
            <v-btn v-else @click="signOut" dark>
                Logout
            </v-btn>
        </v-app-bar>

        <v-main>
            <Breadcrumbs />
            <v-container fluid>
                <router-view />
            </v-container>
            <DndNotification />
        </v-main>

        <v-footer :dark="dark" app>
            <span class="px-3">&copy; {{ new Date().getFullYear() }}</span>
        </v-footer>

        <v-dialog v-model="dialog" max-width="800">
            <Auth @logged-in="dialog = false" />
        </v-dialog>
    </v-app>
</template>

<script>
import { mapState } from 'vuex';

import Breadcrumbs from '@/components/Breadcrumbs';
import DndAppSettings from '@/components/DndAppSettings.vue';
import DndDownloadButton from '@/components/DndDownloadButton.vue';
import DndNotification from '@/components/DndNotification.vue';

import Auth from '@/views/auth';

export default {
    name: 'App',
    components: { DndDownloadButton, Breadcrumbs, DndAppSettings, DndNotification, Auth },
    data: () => ({
        appName: 'D&D Homebrew App',
        displayDrawer: false,
        displaySettings: false,
        transitionName: 'fade',
        dialog: false,
    }),
    computed: {
        ...mapState(['dark']),
        ...mapState('auth', ['username', 'authenticated']),
    },
    methods: {
        displaySettingsPopup() {
            this.displaySettings = true;
        },
        displayAuth() {
            this.dialog = true;
        },
        async signOut() {
            await this.$store.dispatch('auth/signOut');
            await this.$router.push('/');
        },
    },
    mounted() {
        this.$store.dispatch('abilities/load');
        this.$store.dispatch('skills/load');
        this.$store.dispatch('spells/load');
    },
};
</script>
