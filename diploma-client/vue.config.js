const path = require('path');

/**
 * @type {import('@vue/cli-service').ProjectOptions}
 */
module.exports = {
    publicPath: './',

    transpileDependencies: ['vuetify'],

    productionSourceMap: false,

    pluginOptions: {
        dll: {
            entry: {
                vue: ['vue', 'vue-router', 'vue-cookies', 'vue-i18n', 'vuex'],
                vuetify: ['vuetify'],
                kotlin: ['kotlin'],
            },
            cacheFilePath: path.resolve(__dirname, './public')
        }
    },

    configureWebpack: {
        resolve: {
            alias: {
                '@': path.resolve(__dirname, './src'),
            },
        },
    },

    chainWebpack: (config) => {
        // configure SVG loader
        const svgRule = config.module.rule('svg');
        svgRule.uses.clear();
        svgRule
            .use('url-loader')
            .loader('url-loader')
            .options({
                limit: 4096,
                fallback: {
                    loader: 'file-loader',
                    options: {
                        name: 'img/[name].[hash:8].[ext]',
                    },
                },
            });
    },
};
