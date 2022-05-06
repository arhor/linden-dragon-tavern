import React from 'react';

import StatelessWidget from '@/components/StatelessWidget/StatelessWidget.jsx';

export default {
    title: 'Library/StatelessWidget',
    component: StatelessWidget,
};

const Template = (args) => <StatelessWidget {...args} />;

export const Default = Template.bind({});
Default.args = {
    title: 'StatelessWidget title',
    description: 'StatelessWidget description',
};
