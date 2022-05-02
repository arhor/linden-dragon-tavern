const path = require('path');

/**
 * @type {import('@storybook/builder-vite').StorybookViteConfig}
 */
module.exports = {
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
    viteFinal: (config) => ({
        ...config,
        resolve: {
            alias: {
                '@': path.resolve(__dirname, '../src'),
            },
        },
    }),
};
