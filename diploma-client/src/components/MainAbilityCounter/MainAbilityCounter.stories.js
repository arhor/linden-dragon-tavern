import MainAbilityCounter from './MainAbilityCounter.vue';

export default {
    component: MainAbilityCounter,
    title: 'Components/MainAbilityCounter',
};

//👇 We create a “template” of how args map to rendering
const Template = (args, { argTypes }) => ({
    components: { MainAbilityCounter },
    props: Object.keys(argTypes),
    template: '<MainAbilityCounter v-bind="$props"/>',
});

export const Default = Template.bind({});
Default.args = {
    type: 'DEX',
    count: 23,
    modificator: '+2',
};
