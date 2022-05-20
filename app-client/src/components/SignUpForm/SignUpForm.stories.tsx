import { ComponentStoryObj } from '@storybook/react';

import SignUpForm from '@/components/SignUpForm/SignUpForm';

export default {
    title: 'Library/SignUpForm',
    component: SignUpForm,
};

export const Default: ComponentStoryObj<typeof SignUpForm> = {
    args: {},
};
