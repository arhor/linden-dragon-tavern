<template>
    <v-row align="center" justify="center">
        <v-col cols="4">
            <v-form ref="form" v-model="valid" lazy-validation>
                <v-text-field v-model="username.value" v-bind="username.props" />
                <v-text-field v-model="password.value" v-bind="password.props" />
                <v-text-field v-model="email.value" v-bind="email.props" />
                <v-text-field v-model="firstName.value" v-bind="firstName.props" />
                <v-text-field v-model="lastName.value" v-bind="lastName.props" />

                <v-btn :disabled="!valid" color="success" class="mr-4" @click="validate">
                    Validate
                </v-btn>

                <v-btn color="error" class="mr-4" @click="reset">
                    Reset Form
                </v-btn>

                <v-btn color="warning" @click="$emit('sign-in')">
                    Account already exists
                </v-btn>
            </v-form>
        </v-col>
    </v-row>
</template>

<script>
export default {
    name: 'DndSignUp',
    data: () => ({
        valid: true,
        username: {
            value: '',
            props: {
                label: 'Username',
                required: true,
                rules: [
                    (v) => !!v || 'Username is required',
                    (v) => (v && v.length >= 6) || 'Username must be longer than 6 characters',
                    (v) => (v && v.length <= 20) || 'Username must be less than 20 characters',
                    (v) => /^[a-zA-Z0-9]{6,20}$/.test(v) || 'username must contain only letters and numbers',
                ],
            },
        },
        password: {
            value: '',
            props: {
                label: 'Password',
                required: true,
                rules: [
                    (v) => !!v || 'Password is required',
                    (v) => (v && v.length <= 10) || 'Password must be less than 10 characters',
                ],
            },
        },
        email: {
            value: '',
            props: {
                label: 'E-mail',
                required: true,
                rules: [(v) => !!v || 'E-mail is required', (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid'],
            },
        },
        firstName: {
            value: '',
            props: {
                label: 'First Name',
                rules: [(v) => (v && v.length <= 10) || 'Name must be less than 10 characters'],
            },
        },
        lastName: {
            value: '',
            props: {
                label: 'Last Name',
                rules: [(v) => (v && v.length <= 10) || 'Name must be less than 10 characters'],
            },
        },
    }),

    methods: {
        validate() {
            this.$refs.form.validate();
        },
        reset() {
            this.$refs.form.reset();
        },
        resetValidation() {
            this.$refs.form.resetValidation();
        },
    },
};
</script>
