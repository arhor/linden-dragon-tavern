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
        />

        <v-app-bar :dark="dark" app>
            <v-app-bar-nav-icon @click.stop="displayDrawer = !displayDrawer" />

            <v-toolbar-title>{{ appName }}</v-toolbar-title>

            <v-spacer />

            <v-btn v-for="{ path, name } in appBarLinks" :key="name" :to="path" text>
                {{ name }}
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
import DndBreadcrumbs from '@/components/DndBreadcrumbs';

const MODULES_TO_LOAD = ['abilities', 'spells', 'skills'];

export default {
    name: 'App',
    components: { DndBreadcrumbs },
    data: () => ({
        appName: 'D&D Homebrew App',
        displayDrawer: false,
        appBarLinks: [
            { path: '/', name: 'Home' },
            { path: '/about', name: 'About' },
            { path: '/settings', name: 'Settings' },
        ],
    }),
    computed: {
        ...mapState(['dark']),
    },
    mounted() {
        MODULES_TO_LOAD.forEach((asset) => void this.$store.dispatch(`${asset}/load`));
    },
};
</script>
