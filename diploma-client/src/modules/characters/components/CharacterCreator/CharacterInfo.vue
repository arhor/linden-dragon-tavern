<template>
    <v-card class="mx-6" color="grey lighten-1" height="400px">
        <v-row justify="space-around" class="py-3">
            <span class="white--text">{{ $t('name') }}: {{ name }}</span>
        </v-row>

        <v-row
            v-for="(abilityRow, i) in abilityRows"
            :key="`ability-row-${i}`"
            justify="space-around"
            class="py-5 px-16"
        >
            <v-avatar
                v-for="(ability, j) in abilityRow"
                :key="`ability-${j}`"
                color="secondary"
                size="60"
            >
                <span class="white--text">
                    {{ $t(ability.title).toUpperCase() }}
                    <br />
                    {{ ability.value }}
                </span>
            </v-avatar>
        </v-row>
    </v-card>
</template>

<script>
export default {
    name: 'CharacterInfo',

    props: {
        name: String,
        str: { type: Number, required: true },
        dex: { type: Number, required: true },
        con: { type: Number, required: true },
        int: { type: Number, required: true },
        wis: { type: Number, required: true },
        cha: { type: Number, required: true },
    },

    computed: {
        abilityRows() {
            return [
                [
                    { title: 'STR', value: this.str },
                    { title: 'DEX', value: this.dex },
                    { title: 'CON', value: this.con },
                ],
                [
                    { title: 'INT', value: this.int },
                    { title: 'WIS', value: this.wis },
                    { title: 'CHA', value: this.cha },
                ],
            ];
        },
    },
};
</script>
