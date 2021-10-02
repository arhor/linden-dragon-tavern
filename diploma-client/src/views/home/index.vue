<template>
    <v-row align="center" justify="center" align-content="center">
        <v-col cols="12">
            <v-img :src="require('@/assets/svg/logo.svg')" class="my-3" contain height="200" />
        </v-col>
        <v-col cols="12">
            <h1 class="display-2 font-weight-bold mb-3 text-center">
                Welcome to D&D Homebrew App!
            </h1>
        </v-col>
        <v-col cols="6" align-self="center">
            <v-simple-table height="300px">
                <template v-slot:default>
                    <thead>
                        <tr>
                            <th class="text-left" id="name">Name</th>
                            <th class="text-left" id="status">Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr
                            v-for="{ name, path, status } in modules"
                            :key="name"
                            @click.stop="navigateTo(path)"
                        >
                            <td>{{ name }}</td>
                            <td>{{ status }}</td>
                        </tr>
                    </tbody>
                </template>
            </v-simple-table>
        </v-col>
    </v-row>
</template>

<script>
export default {
    name: 'Home',
    computed: {
        modules: () => [
            { name: 'Monsters', path: '/monsters', status: 'in progress' },
            { name: 'Spells', path: '/spells', status: 'in progress' },
            { name: 'Maps', path: '/maps', status: 'in progress' },
            { name: 'Characters', path: '/characters', status: 'in progress' },
        ],
    },
    methods: {
        navigateTo(path) {
            this.$router.push(path);
        },
    },
};
</script>
