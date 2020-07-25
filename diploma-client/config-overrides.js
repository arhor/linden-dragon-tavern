const path = require('path');
const CompressionPlugin = require('compression-webpack-plugin');

module.exports = function override(config) {
    config.resolve = {
        ...config.resolve,
        alias: {
            '@': path.resolve(__dirname, 'src'),
        },
    };

    config.plugins = [
        ...config.plugins,
        new CompressionPlugin({
            filename: '[path].gz[query]',
            algorithm: 'gzip',
            test: new RegExp('\\.(js|jsx|css)$'),
            threshold: 10240,
            minRatio: 0.8,
        }),
    ];

    return config;
};
