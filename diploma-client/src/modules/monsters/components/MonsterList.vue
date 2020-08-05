<template>
    <v-row align="center" justify="center">
        <v-col cols="12">
            <v-card>
                <v-card-title>
                    Monsters
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
                    :items="allMonsters"
                    :search="search"
                    @click:row="showDetails"
                >
                    <template v-slot:items="props" @click.stop="showDetails(props.item)">
                        <td>{{ props.item.name }}</td>
                        <td>{{ props.item.size }}</td>
                        <td>{{ props.item.type }}</td>
                        <td>{{ props.item.challenge_rating }}</td>
                    </template>
                </v-data-table>
                <v-dialog v-model="dialog" max-width="800">
                    <MonsterDetails :monster="monster" />
                </v-dialog>
            </v-card>
        </v-col>
    </v-row>
</template>

<script>
import { mapState } from 'vuex';
import MonsterDetails from '@/modules/monsters/components/MonsterDetails.vue';

export default {
    name: 'MonsterList',
    components: {
        MonsterDetails
    },
    data: () => ({
        search: '',
        dialog: false,
        monster: null,
        errors: [],
        headers: [
            { text: 'Name', value: 'name' },
            { text: 'Size', value: 'size' },
            { text: 'Type', value: 'type' },
            { text: 'CR', value: 'challenge_rating' }
        ]
    }),
    methods: {
        showDetails(concreteMonster) {
            this.monster = concreteMonster;
            this.dialog = true;
        }
    },
    computed: {
        ...mapState('monsters', ['allMonsters'])
    }
};
</script>
