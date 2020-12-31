import * as coreUtils from '@/utils/coreUtils.js';

describe('refExists', () => {
    test.each`
        reference    | expected
        ${0}         | ${true}
        ${''}        | ${true}
        ${[]}        | ${true}
        ${{}}        | ${true}
        ${false}     | ${true}
        ${null}      | ${false}
        ${undefined} | ${false}
        ${void 0}    | ${false}
    `('should return "$expected" for "$reference"', ({ reference, expected }) => {
        // when
        const result = coreUtils.refExists(reference);

        // then
        expect(result).toBe(expected);
    });
});
