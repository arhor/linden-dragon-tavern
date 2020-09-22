<template>
    <generic-list title="Skills" :items="skills" />
</template>

<script>
import { mapState } from 'vuex';
import { signed, toPropName } from '@/utils/stringUtils';
import GenericList from '@/components/generic/GenericList.vue';

export default {
    name: 'SkillList',
    props: {
        creature: {
            type: Object,
            default: null
        }
    },
    components: {
        GenericList
    },
    computed: mapState({
        allSkills: (state) => state.skills.all
    }),
    data: () => ({
        skills: []
    }),
    mounted() {
        const isTraversable = this.allSkills && this.allSkills instanceof Array;
        if (isTraversable) {
            this.allSkills.forEach((it) => {
                const propName = toPropName(it.name);
                const value = this.creature[propName];
                if (value) {
                    this.skills.push(`${it.name} ${signed(value)}`);
                }
            });
        }
    }
};
</script>
