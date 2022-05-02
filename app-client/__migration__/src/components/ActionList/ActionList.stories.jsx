import React from 'react';
import ActionList from '@/components/ActionList/ActionList.jsx';

export default {
    title: 'Library/ActionList',
    component: ActionList,
};

const Template = (args) => <ActionList {...args} />;

export const Default = Template.bind({});
Default.args = {
    actions: [
        { name: 'Attack', desc: 'Strikes an enemy' },
        { name: 'Block', desc: "Prevents enemy's attack" },
    ],
};
