import { ComponentStoryObj } from '@storybook/react';

import SignUp from '@/pages/SignUp/SignUp';

export default {
    title: 'Library/SignUp',
    component: SignUp,
};

export const Default: ComponentStoryObj<typeof SignUp> = {
    args: {},
};
