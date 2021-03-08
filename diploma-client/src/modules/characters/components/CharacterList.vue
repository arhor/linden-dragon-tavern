<template>
    <v-row align="center" justify="center">
        <v-col cols="12">
            <v-card>
                <v-card-title>
                    Characters
                    <v-spacer />
                    <v-btn to="/new-character" text>+ New Character</v-btn>
                </v-card-title>
                <v-data-table
                    class="elevation-1"
                    :headers="headers"
                    :items="characters"
                    @click:row="showDetails"
                >
                    <template
                        v-slot:items="{ item: { id, name, classes } }"
                        @click.stop="showDetails(id)"
                    >
                        <td>{{ name }}</td>
                        <td>{{ classes.join(', ') }}</td>
                    </template>
                </v-data-table>
            </v-card>
        </v-col>
    </v-row>
</template>

<script>
export default {
    name: 'CharacterList',
    props: {
        characters: {
            type: Array,
            required: true,
        },
    },
    data: () => ({
        errors: [],
        headers: [
            { text: 'Name', value: 'name' },
            { text: 'Class', value: 'classes' },
        ],
    }),
    methods: {
        showDetails(characterId) {
            this.$emit('show-details', characterId);
        },
    },
};
</script>
