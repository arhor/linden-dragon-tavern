import { ComponentStoryObj } from '@storybook/react';

import MainAbilityCounter, { Ability } from '@/components/MainAbilityCounter/MainAbilityCounter';

export default {
    title: 'Library/MainAbilityCounter',
    component: MainAbilityCounter,
};

export const Default: ComponentStoryObj<typeof MainAbilityCounter> = {
    args: {
        name: Ability.INT,
        value: 20,
    }
};
