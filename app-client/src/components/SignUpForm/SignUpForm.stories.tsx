import { ComponentMeta, ComponentStoryObj } from '@storybook/react';

import SignUpForm from '@/components/SignUpForm/SignUpForm';
import { withMemoryRouter } from '@/utils/dev/router-utils';

export default {
    title: 'Library/SignUpForm',
    component: withMemoryRouter(SignUpForm),
} as ComponentMeta<typeof SignUpForm>;

export const Default: ComponentStoryObj<typeof SignUpForm> = {
    args: {},
};
