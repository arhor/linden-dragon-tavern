import { ComponentMeta, ComponentStoryObj } from '@storybook/react';

import SignInForm from '@/components/SignInForm';
import { withMemoryRouter } from '@/utils/dev/router-utils';

export default {
    title: 'Library/SignInForm',
    component: withMemoryRouter(SignInForm),
} as ComponentMeta<typeof SignInForm>;

export const Default: ComponentStoryObj<typeof SignInForm> = {
    args: {},
};
