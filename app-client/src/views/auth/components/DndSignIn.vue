<template>
    <v-card>
        <v-card-title>
            <span class="text-h5">Login Form</span>
        </v-card-title>
        <v-card-text>
            <v-container>
                <v-row>
                    <div v-if="error" color="error">Username or Password is incorrect</div>

                    <v-col cols="12">
                        <v-text-field v-model="username" label="Username" required />
                    </v-col>
                    <v-col cols="12">
                        <v-text-field v-model="password" label="Password" required type="password" />
                    </v-col>
                </v-row>
            </v-container>
        </v-card-text>
        <v-card-actions>
            <v-spacer />
            <v-btn color="blue darken-1" text @click="trySignIn">
                Sign In
            </v-btn>
            <v-btn color="blue darken-1" text @click="$emit('sign-up')">
                Register
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
export default {
    name: 'DndSignIn',
    data: () => ({
        username: '',
        password: '',
        error: false,
    }),
    methods: {
        async trySignIn() {
            this.error = false;
            const success = await this.$store.dispatch('auth/signIn', {
                username: this.username,
                password: this.password,
            });
            if (success) {
                this.username = '';
                this.password = '';
                this.$emit('logged-in');
            } else {
                this.error = true;
            }
        },
    },
};
</script>
