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

    devServer: {
        proxy: {
            '^/api': {
                target: 'http://localhost:5000',
                ws: true,
                changeOrigin: true
            }
        }
    },

    configureWebpack: {
        resolve: {
            alias: {
                '@': path.resolve(__dirname, './src'),
            },
        },
    },
};
