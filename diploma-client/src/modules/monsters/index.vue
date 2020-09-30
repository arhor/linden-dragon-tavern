<template>
    <div>
        <monster-list :monsters="allMonsters" @show-monster-details="loadMonsterDetails" />
        <v-dialog v-model="dialog" max-width="800">
            <monster-details :monster="currentMonster" />
        </v-dialog>
    </div>
</template>

<script>
import { mapActions, mapState } from 'vuex';

import MonsterDetails from '@/modules/monsters/components/MonsterDetails.vue';
import MonsterList from '@/modules/monsters/components/MonsterList.vue';

export default {
    components: {
        MonsterDetails,
        MonsterList,
    },
    data: () => ({
        dialog: false,
    }),
    computed: {
        ...mapState('monsters', ['allMonsters', 'currentMonster']),
    },
    methods: {
        ...mapActions('monsters', ['load', 'loadDetails']),
        async loadMonsterDetails({ name }) {
            await this.loadDetails(name);
            this.dialog = true;
        },
    },
    mounted() {
        this.load();
    },
};
</script>
