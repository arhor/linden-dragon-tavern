import * as stringUtils from '@/utils/stringUtils.js';

describe('stringUtils.js', () => {
    test('deserialize', () => {
        const string = 'enabled;timeout=10;';
        const result = stringUtils.deserialize(string);

        expect(result).toHaveProperty('enabled', true);
        expect(result).toHaveProperty('timeout', '10');
    });
});
