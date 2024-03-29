import { ComponentMeta, ComponentStoryObj } from '@storybook/react';

import ActionList from '@/components/ActionList';

export default {
    title: 'Library/ActionList',
    component: ActionList,
} as ComponentMeta<typeof ActionList>;

export const Default: ComponentStoryObj<typeof ActionList> = {
    args: {
        actions: [
            { name: 'Attack', desc: 'Strikes an enemy' },
            { name: 'Block', desc: "Prevents enemy's attack" },
        ],
    },
};
