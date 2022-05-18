import * as path from 'path';

import { StorybookViteConfig } from '@storybook/builder-vite';

const config: StorybookViteConfig = {
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
    },
    viteFinal: async (config) => ({
        ...config,
        resolve: {
            alias: {
                '@': path.resolve(__dirname, '../src'),
            },
        },
    }),
};

export default config;
