import { ComponentStoryObj } from '@storybook/react';

import CreatureList from '@/components/CreatureList';

export default {
    title: 'Library/CreatureList',
    component: CreatureList,
};

export const Default: ComponentStoryObj<typeof CreatureList> = {
    args: {
        items: [
            {
                name: 'Aboleth',
                size: 'Large',
                type: 'aberration',
                alignment: 'lawful evil',
            },
            {
                name: 'Doppelganger',
                size: 'Medium',
                type: 'monstrosity',
                alignment: 'unaligned',
            },
            {
                name: 'Giant Centipede',
                size: 'Small',
                type: 'beast',
                alignment: 'unaligned'
            },
            {
                name: 'Purple Worm',
                size: 'Gargantuan',
                type: 'monstrosity',
                alignment: 'unaligned'
            },
            {
                name: 'Young Copper Dragon',
                size: 'Large',
                type: 'dragon',
                alignment: 'chaotic good'
            },
        ]
    },
};
