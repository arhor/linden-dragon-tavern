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

            <dnd-download-button url="/api/charsheets" />

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

let sseClient;

export default {
    name: 'App',
    components: { DndDownloadButton, DndBreadcrumbs, DndAppSettings },
    data: () => ({
        appName: 'D&D Homebrew App',
        displayDrawer: false,
        displaySettings: false,
        transitionName: 'fade',
        appBarLinks: [
            { path: '/', name: 'Home' },
            { path: '/about', name: 'About' },
        ],
    }),
    computed: {
        ...mapState(['dark']),
    },
    methods: {
        displaySettingsPopup() {
            this.displaySettings = true;
        },
    },
    mounted() {
        this.$store.dispatch('abilities/load');
        this.$store.dispatch('skills/load');
        this.$store.dispatch('spells/load');

        console.log('trying to subscribe on SSE notifications...');

        sseClient = this.$sse.create({ url: '/api/notifications/stream', format: 'json' });

        // Catch any errors (ie. lost connections, etc.)
        sseClient.on('error', (e) => {
            console.error('lost connection or failed to parse!', e);

            // If this error is due to an unexpected disconnection, EventSource will
            // automatically attempt to reconnect indefinitely. You will _not_ need to
            // re-add your handlers.
        });

        sseClient.on('message', (it) => console.log('simple message', it));
        sseClient.on('notification-event', (it) => console.log('notification-event', it));

        sseClient
            .connect()
            .then((sse) => {
                console.log("We're connected!", sse);
            })
            .catch((err) => {
                // When this error is caught, it means the initial connection to the
                // events server failed.  No automatic attempts to reconnect will be made.
                console.error('Failed to connect to server', err);
            });
    },
    beforeDestroy() {
        // Make sure to close the connection with the events server
        // when the component is destroyed, or we'll have ghost connections!
        sseClient.disconnect();

        // Alternatively, we could have added the `sse: { cleanup: true }` option to our component,
        // and the SSEManager would have automatically disconnected during beforeDestroy.
    },
};
</script>
