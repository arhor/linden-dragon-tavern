<template>
    <dnd-comma-separated-list title="Skills" :items="skills" />
</template>

<script>
import {mapState} from 'vuex';

import DndCommaSeparatedList from '@/components/DndCommaSeparatedList.vue';
import {replaceSpacesWithUnderscore, signed} from '@/utils/stringUtils.js';

export default {
    name: 'SkillList',
    props: {
        creature: {
            type: Object,
            default: null,
        },
    },
    components: {
        DndCommaSeparatedList,
    },
    computed: {
        ...mapState('skills', ['allSkills']),
    },
    data: () => ({
        skills: [],
    }),
    mounted() {
        const { allSkills } = this;
        if (allSkills && allSkills instanceof Array) {
            allSkills.forEach((it) => {
                const propName = replaceSpacesWithUnderscore(it.name);
                const value = this.creature[propName];
                if (value) {
                    this.skills.push(`${it.name} ${signed(value)}`);
                }
            });
        }
    },
};
</script>
