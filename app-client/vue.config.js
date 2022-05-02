const path = require('path');
const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
    transpileDependencies: ['vuetify'],
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
                '@': path.resolve(__dirname, 'src'),
            },
        },
    },
    css: {
        extract: { ignoreOrder: true },
    },
});
