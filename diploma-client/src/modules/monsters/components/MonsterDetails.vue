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
                    Armor class: {{ monster.armor_class }}
                </v-col>
                <v-col cols="12" class="text-xs-left">
                    Hit points: {{ monster.hit_points }} ({{ monster.hit_dice }})
                </v-col>
                <v-col cols="12" class="text-xs-left">Speed: {{ monster.speed }}</v-col>
            </v-row>

            <v-divider />

            <ability-list :creature="monster" />

            <v-divider />

            <generic-list title="Damage vulnerabilities" :items="monster.damage_vulnerabilities" />
            <generic-list title="Damage resistances" :items="monster.damage_resistances" />
            <generic-list title="Damage immunities" :items="monster.damage_immunities" />

            <generic-list title="Condition immunities" :items="monster.condition_immunities" />
            <skill-list :creature="monster" />

            <generic-list title="Senses" :items="[monster.senses]" />
            <generic-list title="Languages" :items="[monster.languages]" />

            <v-row>
                <v-col cols="12" class="text-xs-left">
                    <strong>Challenge:</strong>
                    {{ monster.challenge_rating }} (# XP)
                </v-col>
            </v-row>

            <special-ability-list :special-abilities="monster.special_abilities" />

            <v-divider />

            <action-list :actions="monster.actions" />
        </v-container>
    </v-card>
</template>

<script>
import AbilityList from '@/modules/ability/components/AbilityList.vue';
import ActionList from '@/components/action/ActionList.vue';
import GenericList from '@/components/generic/GenericList.vue';
import SkillList from '@/modules/skill/components/SkillList.vue';
import SpecialAbilityList from '@/modules/ability/components/special/SpecialAbilityList.vue';

export default {
    name: 'MonsterDetails',
    props: {
        monster: {
            type: Object,
            default: null
        }
    },
    components: {
        AbilityList,
        ActionList,
        GenericList,
        SkillList,
        SpecialAbilityList
    }
};
</script>
