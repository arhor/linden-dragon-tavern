<template>
    <v-row align="center" justify="center">
        <v-col>
            Account details
        </v-col>
    </v-row>
</template>

<script>
export default {
    name: 'DndAccountDetails',
    data: () => ({
        username: '',
        password: '',
        firstName: '',
        lastName: '',
        email: '',
    }),
};
</script>
