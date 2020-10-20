import * as coreUtils from '@/utils/coreUtils.js';

describe('coreUtils.js', () => {
    test('should return true for any reference which is not null or undefined', () => {
        const existingRefs = [0, '', [], {}, false];

        existingRefs.forEach((obj) => {
            expect(coreUtils.refExists(obj)).toBe(true);
        });
    });

    test('should return false for any reference which is null or undefined', () => {
        const emptyRefs = [null, undefined, void 0];

        emptyRefs.forEach((obj) => {
            expect(coreUtils.refExists(obj)).toBe(false);
        });
    });
});
