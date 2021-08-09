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
                        @change="register"
                    />
                </v-card-title>
                <v-data-table
                    class="elevation-1"
                    :headers="headers"
                    :items="monstersTest"
                    :search="search"
                    :options.sync="options"
                    :server-items-length="totalMonsters"
                    :loading="loading"
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
import axios from '@/api/BaseService.js';
import { SERVER_API_URL } from '@/api/server-api.js';
import { searchMixin } from '@/mixins/searchMixin.js';

export default {
    name: 'MonsterList',
    mixins: [searchMixin],
    props: {
        monsters: {
            type: Array,
            required: true,
        },
    },
    data: () => ({
        errors: [],
        totalMonsters: 0,
        monstersTest: [],
        loading: true,
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
            handler: 'getDataFromApi',
            deep: true,
        },
    },
    methods: {
        showDetails(monsterName) {
            this.$emit('show-monster-details', monsterName);
        },
        async getDataFromApi() {
            this.loading = true

            try {
                const { page, itemsPerPage, sortBy, sortDesc } = this.options;

                let requestURL = `${SERVER_API_URL}/api/v1/monsters?page=${page}&size=${itemsPerPage}`;

                if (sortBy[0] !== null && sortBy[0] !== undefined) {
                    requestURL += `&sortBy=${sortBy[0]}`;
                }
                if (sortDesc[0] !== null && sortDesc[0] !== undefined) {
                    requestURL += `&sortDesc=${sortDesc[0]}`;
                }

                const { data } = await axios.get(requestURL);

                this.monstersTest = data;
                this.totalMonsters = 325;
                this.loading = false;

                console.log(this.options);
            } catch (e) {
                console.error('Failed attempt to fetch monsters list', e);
            }
        },
    },
};
</script>
