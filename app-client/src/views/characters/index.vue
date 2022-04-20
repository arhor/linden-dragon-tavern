<template>
    <div>
        <character-list :characters="allCharacters" @show-details="fetchAndDisplay" />

        <v-dialog v-model="dialog" max-width="800">
            <character-details :character="currentCharacter" />
        </v-dialog>
    </div>
</template>

<script>
import CharacterDetails from '@/views/characters/components/CharacterDetails';
import CharacterList from '@/views/characters/components/CharacterList.vue';

export default {
    components: {
        CharacterDetails,
        CharacterList,
    },
    data: () => ({
        dialog: false,
        currentCharacter: null,
        allCharacters: [],
    }),
    methods: {
        fetchAndDisplay() {
            this.currentCharacter = {
                name: 'Max',
                classes: ['Wizard 20', 'Monk 20', 'Fighter 20'],
            };
            this.dialog = true;
        },
    },
    mounted() {
        this.$store.dispatch(`characterStats/load`);
    },
};
</script>
