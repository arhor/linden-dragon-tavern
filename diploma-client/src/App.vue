<template>
    <v-app dark>
        <v-navigation-drawer
            v-model="displayDrawer"
            mini-variant
            temporary
            absolute
            overflow
            dark
            app
        />

        <v-app-bar dark app>
            <v-app-bar-nav-icon @click.stop="displayDrawer = !displayDrawer" />

            <v-toolbar-title>{{ name }}</v-toolbar-title>

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

        <v-footer dark app>
            <span class="px-3">&copy; {{ new Date().getFullYear() }}</span>
        </v-footer>
    </v-app>
</template>

<script>
import DndBreadcrumbs from '@/components/DndBreadcrumbs';

const MODULES_TO_LOAD = ['abilities', 'monsters', 'spells', 'skills'];

export default {
    name: 'App',
    components: { DndBreadcrumbs },
    data: () => ({
        name: 'D&D Homebrew App',
        displayDrawer: false,
        appBarLinks: [
            { path: '/', name: 'Home' },
            { path: '/about', name: 'About' },
            { path: '/settings', name: 'Settings' }
        ]
    }),
    mounted() {
        MODULES_TO_LOAD.forEach((asset) => void this.$store.dispatch(`${asset}/load`));
    }
};
</script>
