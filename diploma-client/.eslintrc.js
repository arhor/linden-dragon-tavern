module.exports = {
    root: true,

    env: {
        node: true
    },

    settings: {
        'import/resolver': 'webpack'
    },

    extends: ['plugin:vue/essential', 'eslint:recommended', '@vue/prettier'],

    parserOptions: {
        parser: 'babel-eslint'
    },

    plugins: [
        'sort-imports-es6-autofix',
    ],

    rules: {
        'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
        'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
        'no-shadow': 'error',
        'vue/require-prop-types': 'warn',
        'vue/no-confusing-v-for-v-if': 'warn',
        'vue/this-in-template': 'warn',
        'vue/camelcase': 'warn',
        'vue/comma-dangle': 'warn',
        'no-param-reassign': [
            'error', {
                props: true,
                ignorePropertyModificationsFor: ['state']
            }
        ],
        'sort-imports-es6-autofix/sort-imports-es6': [2, {
            'ignoreCase': false,
            'ignoreMemberSort': false,
            'memberSyntaxSortOrder': ['none', 'single', 'multiple', 'all']
        }],
    },

    overrides: [
        {
            files: [
                '**/__tests__/*.{j,t}s?(x)',
                '**/tests/unit/**/*.spec.{j,t}s?(x)'
            ],
            env: {
                jest: true
            }
        }
    ]
};
