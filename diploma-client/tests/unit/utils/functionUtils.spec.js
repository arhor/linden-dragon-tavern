import * as functionUtils from '@/utils/functionUtils.js';

describe('functionUtils.js', () => {
    test('should return argument as is', () => {
        const value = { some: 'complex object' };
        const result = functionUtils.identity(value);

        expect(result).toBe(value);
    });
});
