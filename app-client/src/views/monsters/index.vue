<template>
    <div>
        <monster-list
            :items="items"
            :total="total"
            @show-monster-details="loadMonsterDetails"
            @get-monsters-page="loadMonstersPage"
        />
        <v-dialog v-model="dialog" max-width="800">
            <monster-details :monster="currentMonster" />
        </v-dialog>
    </div>
</template>

<script>
import {mapActions, mapState} from 'vuex';

import MonsterDetails from '@/views/monsters/components/MonsterDetails.vue';
import MonsterList from '@/views/monsters/components/MonsterList.vue';

export default {
    components: {
        MonsterDetails,
        MonsterList,
    },
    data: () => ({
        dialog: false,
    }),
    computed: {
        ...mapState('monsters', ['items', 'total', 'currentMonster']),
    },
    methods: {
        ...mapActions('monsters', ['loadMonstersPage', 'loadDetails']),
        async loadMonsterDetails({ name }) {
            await this.loadDetails(name);
            this.dialog = true;
        },
    },
};
</script>
