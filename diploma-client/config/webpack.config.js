const path = require('path');

const configureWebpack = {
    resolve: {
        alias: {
            '@': path.resolve(__dirname, 'src'),
        },
    },
};

const chainWebpack = (config) => {
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
};

module.exports = {
    configureWebpack,
    chainWebpack,
};
