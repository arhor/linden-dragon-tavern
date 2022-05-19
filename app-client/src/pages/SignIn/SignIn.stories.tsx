import { ComponentStoryObj } from '@storybook/react';

import SignIn from '@/pages/SignIn';

export default {
    title: 'Library/SignIn',
    component: SignIn,
};

export const Default: ComponentStoryObj<typeof SignIn> = {
    args: {},
};
