import { ComponentStoryObj } from '@storybook/react';

import SignInForm from '@/components/SignInForm';

export default {
    title: 'Library/SignInForm',
    component: SignInForm,
};

export const Default: ComponentStoryObj<typeof SignInForm> = {
    args: {},
};
