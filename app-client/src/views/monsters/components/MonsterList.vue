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
                    class="elevation-1"
                    :headers="headers"
                    :items="items"
                    :search="search"
                    :options.sync="options"
                    :server-items-length="total"
                    @click:row="showDetails"
                >
                    <template
                        v-slot:items="{ item: { name, size, type, challengeRating } }"
                        @click.stop="showDetails(name)"
                    >
                        <td>{{ name }}</td>
                        <td>{{ size }}</td>
                        <td>{{ type }}</td>
                        <td>{{ challengeRating }}</td>
                    </template>
                </v-data-table>
            </v-card>
        </v-col>
    </v-row>
</template>

<script>
import {searchMixin} from '@/mixins/searchMixin.js';

export default {
    name: 'MonsterList',
    mixins: [searchMixin],
    props: {
        items: {
            type: Array,
            required: true,
        },
        total: {
            type: Number,
            required: true,
        },
    },
    data: () => ({
        options: {},
        headers: [
            { text: 'Name', value: 'name' },
            { text: 'Size', value: 'size' },
            { text: 'Type', value: 'type' },
            { text: 'CR', value: 'challengeRating' },
        ],
    }),
    watch: {
        options: {
            handler: 'getMonstersPage',
            deep: true,
        },
    },
    methods: {
        showDetails(monsterName) {
            this.$emit('show-monster-details', monsterName);
        },
        getMonstersPage() {
            this.$emit('get-monsters-page', this.options);
        },
    },
};
</script>
