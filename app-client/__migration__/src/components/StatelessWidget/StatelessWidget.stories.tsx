import { ComponentStoryObj } from '@storybook/react';

import StatelessWidget from '@/components/StatelessWidget/StatelessWidget';

export default {
    title: 'Library/StatelessWidget',
    component: StatelessWidget,
};

export const Default: ComponentStoryObj<typeof StatelessWidget> = {
    args: {
        title: 'StatelessWidget title',
        description: 'StatelessWidget description',
    },
};
