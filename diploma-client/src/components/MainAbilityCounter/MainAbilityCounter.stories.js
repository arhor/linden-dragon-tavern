import MainAbilityCounter from '@/components/MainAbilityCounter/MainAbilityCounter.vue';

export default {
    component: MainAbilityCounter,
    title: 'Components/MainAbilityCounter',
};

//👇 We create a “template” of how args map to rendering
const Template = (args, { argTypes }) => ({
    components: { MainAbilityCounter },
    props: Object.keys(argTypes),
    template: '<main-ability-counter v-bind="$props"/>',
});

export const Default = Template.bind({});
Default.args = {
    type: 'DEX',
    count: 23,
    modifier: '+2',
};
