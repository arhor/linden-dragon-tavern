module.exports = {
  root: true,
  env: {
    node: true
  },
  'extends': [
    'plugin:vue/essential',
    'eslint:recommended'
  ],
  parserOptions: {
    parser: 'babel-eslint'
  },
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-plusplus': [
      'error', {
        'allowForLoopAfterthoughts': true
      }
    ],
    'no-param-reassign': [
      'error', {
        'props': true,
        'ignorePropertyModificationsFor': ['state']
      }
    ],
    'no-shadow': [
      'error', {
        'allow': ['state']
      }
    ]
  }
}