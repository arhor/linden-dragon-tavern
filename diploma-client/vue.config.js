const path = require('path');

module.exports = {
    publicPath: './',

    transpileDependencies: ['vuetify'],

    productionSourceMap: true,

    devServer: {
        proxy: {
            '^/api': {
                target: 'http://localhost:5000',
                ws: true,
                changeOrigin: true,
            },
        },
    },

    configureWebpack: {
        resolve: {
            alias: {
                '@': path.resolve(__dirname, './src'),
            },
        },
    },

    css: {
        extract: { ignoreOrder: true },
    },
};
