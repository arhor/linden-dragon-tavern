import { ComponentStoryObj } from '@storybook/react';

import Loader from '@/components/Loader';

export default {
    title: 'Library/Loader',
    component: Loader,
};

export const Default: ComponentStoryObj<typeof Loader> = {
    args: {},
};
