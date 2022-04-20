import ActionList from '@/components/ActionList';

export default {
    component: ActionList,
    title: 'Components/ActionList',
};

const Template = (args, { argTypes }) => ({
    components: { ActionList },
    props: Object.keys(argTypes),
    template: '<ActionList v-bind="$props"/>',
});

export const Default = Template.bind({});

Default.args = {
    actions: [
        { name: 'Attack', desc: 'Strikes an enemy' },
        { name: 'Block', desc: "Prevents enemy's attack" },
    ],
};
