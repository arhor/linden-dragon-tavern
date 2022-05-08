import * as arrayUtils from '@/utils/arrayUtils';

describe('isEmptyArray', () => {
    test.each([[[]], [new Array()], [Array.of()]])('should return true only for array instance', (array) => {
        // when
        const result = arrayUtils.isEmptyArray(array);

        // then
        expect(result).toBe(true);
    });

    test.each([[null], [undefined], ['array'], [10], [true], [false]])('should return false for "%s"', (value) => {
        // when
        const result = arrayUtils.isEmptyArray(value);

        // then
        expect(result).toBe(false);
    });

    test.each([[[1]], [new Array(true, false)], [Array.of('one', 'two', 'three')], [Array.from('array')]])(
        'should return false for the array: %s',
        (array) => {
            // when
            const result = arrayUtils.isEmptyArray(array);

            // then
            expect(result).toBe(false);
        },
    );
});

describe('commaSeparate', () => {
    test('should return comma separated string representation of passed array', () => {
        // given
        const arrayToJoin = ['one', 'two', 'three'];

        // when
        const result = arrayUtils.commaSeparate(arrayToJoin);

        // then
        expect(result).toMatch('one, two, three');
    });

    test.each([[null], [undefined], [[]]])('should return empty string for %s', (array) => {
        // when
        const result = arrayUtils.commaSeparate(array);

        // then
        expect(result).toMatch('');
    });
});
