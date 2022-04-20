<template>
    <v-snackbar v-model="snackbar" timeout="2000">
        {{ text }}
        <template v-slot:action="{ attrs }">
            <v-btn :color="color" text v-bind="attrs" @click="snackbar = false">
                Close
            </v-btn>
        </template>
    </v-snackbar>
</template>

<script>
import {mapState} from 'vuex';
import {refExists} from '@/utils/coreUtils';

const NOTIFICATION_CLIENT_OPTIONS = Object.freeze({
    url: '/api/notifications/stream',
    format: 'json',
});

let notificationClient;

export default {
    name: 'DndNotification',
    data: () => ({
        snackbar: false,
        color: '',
        text: '',
        ...mapState('auth', ['authenticated']),
    }),
    methods: {
        displayNotification({ type, message }) {
            if (type === 'INFO') {
                this.color = 'blue';
            } else if (type === 'ERROR') {
                this.color = 'red';
            } else {
                this.color = 'gray';
            }

            this.text = message;
            this.snackbar = true;
        },
    },
    watch: {
        authenticated(value) {
            if (!value) {
                notificationClient?.disconnect();
            }
        },
    },
    async mounted() {
        if (this.authenticated) {
            if (!refExists(notificationClient)) {
                notificationClient = this['$sse'].create(NOTIFICATION_CLIENT_OPTIONS);
            }

            notificationClient.on('notification-event', this.displayNotification);

            try {
                const sse = await notificationClient.connect();
                console.log('Successfully connected to notification server', sse);
            } catch (err) {
                console.error('Failed to connect to notification server', err);
            }
        }
    },
    beforeDestroy() {
        notificationClient?.disconnect();
    },
};
</script>
