const path = require('path');
const CompressionPlugin = require('compression-webpack-plugin');

module.exports = {
    transpileDependencies: ['vuetify'],
    configureWebpack: {
        resolve: {
            alias: {
                '@': path.resolve(__dirname, 'src'),
            },
        },
        plugins: [
            new CompressionPlugin({
                filename: '[path].gz[query]',
                algorithm: 'gzip',
                test: new RegExp('\\.(js|jsx|css)$'),
                threshold: 10240,
                minRatio: 0.8,
            }),
        ],
    },
};
