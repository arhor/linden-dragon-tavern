import * as arrayUtils from '@/utils/arrayUtils.js';

describe('arrayUtils.js', () => {
    test('isEmptyArray should return true only for array instance', () => {
        const emptyArrays = [[], new Array(), Array.of()];

        emptyArrays.forEach((array) => {
            expect(arrayUtils.isEmptyArray(array)).toBe(true);
        });
    });

    test('isEmptyArray should return false for null, undefined, strings etc.', () => {
        const nonArrays = [null, undefined, 'array', 10, true, false];

        nonArrays.forEach((some) => {
            expect(arrayUtils.isEmptyArray(some)).toBe(false);
        });
    });

    test('isEmptyArray should return true for any filled array', () => {
        const filledArrays = [
            [1],
            new Array(true, false),
            Array.of('one', 'two', 'three'),
            Array.from('array'),
        ];

        filledArrays.forEach((array) => {
            expect(arrayUtils.isEmptyArray(array)).toBe(false);
        });
    });

    test('commaSeparate should return comma separated string representation of passed array', () => {
        const arrayToJoin = ['one', 'two', 'three'];

        expect(arrayUtils.commaSeparate(arrayToJoin)).toMatch('one, two, three');
    });

    test('commaSeparate should return empty string for null, undefined or epmty array', () => {
        const arrays = [null, undefined, []];

        arrays.forEach((array) => {
            expect(arrayUtils.commaSeparate(array)).toMatch('');
        });
    });
});
