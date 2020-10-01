<template>
    <v-row align="center" justify="center">
        <v-col cols="4">
            <v-form ref="form" lazy-validation>
                <v-text-field v-model="username" label="Username" required />
                <v-text-field v-model="password" label="Password" required />

                <v-btn color="success" class="mr-4">
                    Sign In
                </v-btn>

                <v-btn color="error" class="mr-4" @click.stop="$emit('sign-up')">
                    Register
                </v-btn>
            </v-form>
        </v-col>
    </v-row>
</template>

<script>
export default {
    name: 'DndSignIn',
    data: () => ({
        username: '',
        password: '',
    }),
};
</script>
