module.exports = {
    root: true,
    env: {
        jest: true,
        node: true,
        browser: true,
    },
    extends: [
        'eslint:recommended',
        'plugin:react/recommended',
    ],
    parserOptions: {
        sourceType: 'module',
        ecmaVersion: 'latest',
        ecmaFeatures: {
            jsx: true,
        },
    },
    plugins: [
        'sort-imports-es6-autofix',
    ],
    rules: {
        'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
        'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
        'sort-imports-es6-autofix/sort-imports-es6': [
            2,
            {
                ignoreCase: false,
                ignoreMemberSort: false,
                memberSyntaxSortOrder: [
                    'all',
                    'single',
                    'multiple',
                    'none',
                ],
            },
        ],
    },
};
