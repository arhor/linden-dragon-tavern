import React from 'react';

import MainAbilityCounter, { ABILITY } from '@/components/MainAbilityCounter/MainAbilityCounter.jsx';

export default {
    title: 'Library/MainAbilityCounter',
    component: MainAbilityCounter,
};

const Template = (args) => <MainAbilityCounter {...args} />;

export const Default = Template.bind({});
Default.args = {
    name: ABILITY.INT,
    value: 20,
};
