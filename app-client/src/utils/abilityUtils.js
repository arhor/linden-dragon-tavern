import { signed } from '@/utils/stringUtils';

const ABILITY_NAME_START = 0;
const ABILITY_NAME_LENGTH = 3;

export function minifyAbilityName(name) {
    return name?.toUpperCase()?.substr(ABILITY_NAME_START, ABILITY_NAME_LENGTH) ?? '';
}

const MEDIAN = 10 | 0;
const DIVISOR = 2 | 0;
const LOW_ABILITY_CORRECTION = 2;

export function calculateModifier(value) {
    let result = value;
    if (result < MEDIAN) {
        result -= LOW_ABILITY_CORRECTION;
    }
    const modifier = ((result - MEDIAN) / DIVISOR) | 0;
    return signed(modifier);
}
