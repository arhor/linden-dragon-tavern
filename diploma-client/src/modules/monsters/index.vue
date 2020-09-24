<template>
    <div>
        <monster-list :monsters="allMonsters" @show-monster-details="loadMonsterDetails" />
        <v-dialog v-model="dialog" max-width="800">
            <monster-details :monster="currentMonster" />
        </v-dialog>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import { action } from '@/modules/monsters/store';
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
        loadMonsterDetails({ name }) {
            this.$store.dispatch(`monsters/${action.loadDetails}`, name).then(() => {
                this.dialog = true;
            });
        },
    },
};
</script>
