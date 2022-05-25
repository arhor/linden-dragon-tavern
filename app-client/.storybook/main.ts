import path from 'path';

import { StorybookViteConfig } from '@storybook/builder-vite';

export default {
    framework: '@storybook/react',
    core: {
        builder: '@storybook/builder-vite',
    },
    stories: [
        '../src/**/*.stories.mdx',
        '../src/**/*.stories.@(js|jsx|ts|tsx)',
    ],
    addons: [
        '@storybook/addon-links',
        '@storybook/addon-essentials',
        '@storybook/addon-interactions',
    ],
    features: {
        storyStoreV7: true,
        emotionAlias: true,
        modernInlineRendering: true,
    },
    viteFinal: config => ({
        ...config,
        resolve: {
            alias: {
                '@': path.resolve(__dirname, '../src'),
            },
        },
    }),
} as StorybookViteConfig;
