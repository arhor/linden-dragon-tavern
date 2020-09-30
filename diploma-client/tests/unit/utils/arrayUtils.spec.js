import * as arrayUtils from '@/utils/arrayUtils.js';

describe('arrayUtils.js', () => {
    test('should return true only for array instance', () => {
        const emptyArrays = [[], new Array(), Array.of()];

        emptyArrays.forEach((array) => {
            const result = arrayUtils.isEmptyArray(array);

            expect(result).toBe(true);
        });
    });

    test('should return false for null, undefined, strings etc.', () => {
        const nonArrays = [null, undefined, 'array', 10, true, false];

        nonArrays.forEach((some) => {
            const result = arrayUtils.isEmptyArray(some);

            expect(result).toBe(false);
        });
    });

    test('should return true for any filled array', () => {
        const filledArrays = [
            [1],
            new Array(true, false),
            Array.of('one', 'two', 'three'),
            Array.from('array'),
        ];

        filledArrays.forEach((array) => {
            const result = arrayUtils.isEmptyArray(array);

            expect(result).toBe(false);
        });
    });

    test('should return comma separated string representation of passed array', () => {
        const arrayToJoin = ['one', 'two', 'three'];
        const result = arrayUtils.commaSeparate(arrayToJoin);

        expect(result).toMatch('one, two, three');
    });

    test('should return empty string for null, undefined or epmty array', () => {
        const arrays = [null, undefined, []];

        arrays.forEach((array) => {
            const result = arrayUtils.commaSeparate(array);

            expect(result).toMatch('');
        });
    });
});
