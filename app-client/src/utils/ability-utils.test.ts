import * as abilityUtils from '@/utils/ability-utils';

describe('minifyAbilityName', () => {
    test.each([
        ['strength'     , 'STR'],
        [ 'dexterity'   , 'DEX'],
        ['constitution' , 'CON'],
        ['intelligence' , 'INT'],
        ['wisdom'       , 'WIS'],
        ['charisma'     , 'CHA'],
    ])('should return "%s" minifying "%s"', (input, expected) => {
        // when
        const minifiedName = abilityUtils.minifyAbilityName(input);

        // then
        expect(minifiedName).toEqual(expected);
    });
});

describe('calculateModifier', () => {
    test.each([
        [6 , '-3'],
        [7 , '-2'],
        [8 , '-2'],
        [9 , '-1'],
        [10, '+0'],
        [11, '+0'],
        [12, '+1'],
        [13, '+1'],
        [14, '+2'],
    ])('should return modifier of %s for the ability value %s', (value, expected) => {
        // when
        const modifier = abilityUtils.calculateModifier(value);

        // then
        expect(modifier).toEqual(expected);
    });
});
