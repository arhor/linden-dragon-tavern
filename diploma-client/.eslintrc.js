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

    rules: {
        'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
        'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
        'no-param-reassign': [
            'error',
            {
                props: true,
                ignorePropertyModificationsFor: ['state']
            }
        ],
        'no-shadow': [
            'error',
            {
                allow: ['state']
            }
        ]
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
