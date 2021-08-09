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
            this.loading = true;

            try {
                const { page, itemsPerPage, sortBy, sortDesc, search } = this.options;

                let requestURL = `${SERVER_API_URL}/api/v1/monsters?page=${page}&size=${itemsPerPage}`;

                if (sortBy?.length > 0) {
                    requestURL += `&sortBy=${sortBy}`;
                }
                if (sortDesc?.length > 0) {
                    requestURL += `&sortDesc=${sortDesc}`;
                }
                if (search?.length > 0) {
                    requestURL += `&search=${search}`;
                }

                const { data } = await axios.get(requestURL);

                this.monstersTest = data.items;
                this.totalMonsters = data.total;
                this.loading = false;
            } catch (e) {
                console.error('Failed attempt to fetch monsters list', e);
            }
        },
    },
};
</script>
