import * as functionUtils from '@/utils/functionUtils';

describe('identity', () => {
    test('should return argument as is', () => {
        // given
        const value = { some: 'complex object' };

        // when
        const result = functionUtils.identity(value);

        // then
        expect(result).toBe(value);
    });
});

describe('simpleCompose', () => {
    test('should compose two functions into the one', () => {
        // given
        const sum_x2 = (x: number) => x + 2;
        const mul_x2 = (x: number) => x * 2;
        const value = 2;

        // when
        const sumThenMul = functionUtils.simpleCompose(sum_x2, mul_x2);

        // then
        expect(sumThenMul(value)).toBe(mul_x2(sum_x2(value)));
    });
});
