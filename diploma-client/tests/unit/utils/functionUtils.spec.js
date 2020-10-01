import * as functionUtils from '@/utils/functionUtils.js';

describe('functionUtils.js', () => {
    test('should return argument as is', () => {
        const value = { some: 'complex object' };
        const result = functionUtils.identity(value);

        expect(result).toBe(value);
    });

    test('should compose two functions into the one', () => {
        const sum_x2 = (x) => x + 2;
        const mul_x2 = (x) => x * 2;

        const sumThenMul = functionUtils.simpleCompose(sum_x2, mul_x2);

        const value = 2;

        expect(sumThenMul(value)).toBe(mul_x2(sum_x2(value)));
    });
});
