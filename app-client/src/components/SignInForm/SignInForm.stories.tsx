import { ComponentMeta, ComponentStoryObj } from '@storybook/react';
import { MemoryRouter } from 'react-router';

import SignInForm from '@/components/SignInForm';

const SignInFormWithRouter = () => (
    <MemoryRouter>
        <SignInForm />
    </MemoryRouter>
);

export default {
    title: 'Library/SignInForm',
    component: SignInFormWithRouter,
} as ComponentMeta<typeof SignInFormWithRouter>;

export const Default: ComponentStoryObj<typeof SignInFormWithRouter> = {
    args: {},
};
