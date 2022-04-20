import Vue from 'vue';
import VueCompositionApi from '@vue/composition-api';

import MainAbilityCounter from '@/components/MainAbilityCounter/MainAbilityCounter.vue';

Vue.use(VueCompositionApi);

export default {
    component: MainAbilityCounter,
    title: 'Components/MainAbilityCounter',
};

const Template = (args, { argTypes }) => ({
    components: { MainAbilityCounter },
    props: Object.keys(argTypes),
    template: '<MainAbilityCounter v-bind="$props"/>',
});

export const Default = Template.bind({});
Default.args = {
    type: 'DEX',
    count: 23,
};
