import { ComponentMeta, ComponentStoryObj } from '@storybook/react';
import { MemoryRouter } from 'react-router';

import SignUpForm from '@/components/SignUpForm/SignUpForm';

const SignUpFormWithRouter = () => (
    <MemoryRouter>
        <SignUpForm />
    </MemoryRouter>
)

export default {
    title: 'Library/SignUpForm',
    component: SignUpFormWithRouter,
} as ComponentMeta<typeof SignUpFormWithRouter>;

export const Default: ComponentStoryObj<typeof SignUpFormWithRouter> = {
    args: {},
};
