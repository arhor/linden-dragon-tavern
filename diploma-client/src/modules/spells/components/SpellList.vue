<template>
    <v-row align="center" justify="center">
        <v-col cols="12">
            <v-card>
                <v-card-title>
                    Spells
                    <v-spacer />
                    <v-text-field
                        v-model="search"
                        append-icon="mdi-magnify"
                        label="Search"
                        single-line
                        hide-details
                    />
                </v-card-title>
                <v-data-table
                    class="evelation-1"
                    :headers="headers"
                    :items="allSpells"
                    :search="search"
                    @click:row="showDetails"
                >
                    <template v-slot:items="props" @click.stop="showDetails(props.item)">
                        <td>{{ props.item.level }}</td>
                        <td>{{ props.item.name }}</td>
                        <td>{{ props.item.school.name }}</td>
                    </template>
                </v-data-table>
            </v-card>
        </v-col>
    </v-row>
</template>

<script>
import { mapState } from 'vuex';

export default {
    name: 'SpellList',
    data: () => ({
        search: '',
        dialog: false,
        spell: null,
        errors: [],
        headers: [
            { text: 'Level', value: 'level' },
            { text: 'Name', value: 'name' },
            { text: 'School', value: 'school.name' }
        ]
    }),
    methods: {
        showDetails(concreteSpell) {
            this.spell = concreteSpell;
            this.dialog = true;
        }
    },
    computed: {
        ...mapState('spells', ['allSpells'])
    }
};
</script>
