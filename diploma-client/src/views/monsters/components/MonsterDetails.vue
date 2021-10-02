<template>
    <v-card v-if="monster" xs10 md10>
        <v-card-title primary-title>
            <v-row>
                <v-col cols="12" class="headline">{{ monster.name }}</v-col>
            </v-row>
        </v-card-title>

        <v-container grid-list-md>
            <v-row>
                <v-col cols="12" class="text-xs-left">
                    {{ monster.size }} {{ monster.type }}, {{ monster.alignment }}
                </v-col>
            </v-row>

            <v-divider />

            <v-row>
                <v-col cols="12" class="text-xs-left">
                    Armor class: {{ monster.armorClass }}
                </v-col>
                <v-col cols="12" class="text-xs-left"> Hit points: {{ monster.hitPoints }} </v-col>
                <v-col cols="12" class="text-xs-left"> Speed: {{ monster.speed.join(',') }} </v-col>
            </v-row>

            <v-divider />

            <ability-list :creature="monster" />

            <v-divider />

            <dnd-comma-separated-list
                title="Damage vulnerabilities"
                :items="monster.damageVulnerabilities"
            />
            <dnd-comma-separated-list
                title="Damage resistances"
                :items="monster.damageResistances"
            />
            <dnd-comma-separated-list title="Damage immunities" :items="monster.damageImmunities" />
            <dnd-comma-separated-list
                title="Condition immunities"
                :items="monster.conditionImmunities"
            />

            <skill-list :creature="monster" />

            <dnd-comma-separated-list title="Senses" :items="[monster.senses]" />
            <dnd-comma-separated-list title="Languages" :items="[monster.languages]" />

            <v-row>
                <v-col cols="12" class="text-xs-left">
                    <strong>Challenge:</strong>
                    {{ monster.challengeRating }} (# XP)
                </v-col>
            </v-row>

            <v-divider />

            <special-ability-list :special-abilities="monster.specialAbilities" />

            <v-divider />

            <ActionList :actions="monster.actions" />

            <v-divider />

            <special-ability-list :special-abilities="monster.legendaryActions" />
        </v-container>
    </v-card>
</template>

<script>
import AbilityList from '@/views/ability/components/AbilityList.vue';
import ActionList from '@/components/ActionList';
import DndCommaSeparatedList from '@/components/DndCommaSeparatedList.vue';
import SkillList from '@/views/skill/components/SkillList.vue';
import SpecialAbilityList from '@/views/ability/components/special/SpecialAbilityList.vue';

export default {
    name: 'MonsterDetails',
    props: {
        monster: {
            type: Object,
            default: null,
        },
    },
    components: {
        AbilityList,
        ActionList,
        DndCommaSeparatedList,
        SkillList,
        SpecialAbilityList,
    },
};
</script>
