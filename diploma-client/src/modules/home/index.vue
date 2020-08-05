<template>
    <v-row align="center" justify="center" align-content="center">
        <v-col cols="12">
            <v-img src="logo.svg" class="my-3" contain height="200" />
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
                            <th class="text-left">Name</th>
                            <th class="text-left">Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr
                            v-for="module in modules"
                            :key="module.name"
                            @click.stop="goTo(module.path)"
                        >
                            <td>{{ module.name }}</td>
                            <td>{{ module.status }}</td>
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
    data: () => ({
        modules: [
            { name: 'Monsters', path: '/monsters', status: 'in progress' },
            { name: 'Spells', path: '/spells', status: 'in progress' },
            { name: 'Maps', path: '/maps', status: 'in progress' }
        ]
    }),
    methods: {
        goTo(path) {
            this.$router.push(path);
        }
    }
};
</script>
