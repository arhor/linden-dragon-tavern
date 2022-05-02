import * as coreUtils from '@/utils/coreUtils.js';

describe('isUndefined', () => {
    test.each([
        [0,         false],
        ['',        false],
        [[],        false],
        [{},        false],
        [false,     false],
        [null,      false],
        [undefined, true ],
        [void 0,    true ],
    ])('should return "%s" for "%s"', (argument, expected) => {
        // when
        const result = coreUtils.isUndefined(argument);

        // then
        expect(result).toBe(expected);
    });
});

describe('isNull', () => {
    test.each([
        [0         , false],
        [''        , false],
        [[]        , false],
        [{}        , false],
        [false     , false],
        [undefined , false],
        [void 0    , false],
        [null      , true ],
    ])('should return "%s" for "%s"', (argument, expected) => {
        // when
        const result = coreUtils.isNull(argument);

        // then
        expect(result).toBe(expected);
    });
});

describe('refExists', () => {
    test.each([
        [0         , true ],
        [''        , true ],
        [[]        , true ],
        [{}        , true ],
        [false     , true ],
        [null      , false],
        [undefined , false],
        [void 0    , false],
    ])('should return "%s" for "%s"', (argument, expected) => {
        // when
        const result = coreUtils.refExists(argument);

        // then
        expect(result).toBe(expected);
    });
});
