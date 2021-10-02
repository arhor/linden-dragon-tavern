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
    if (value < MEDIAN) {
        value -= LOW_ABILITY_CORRECTION;
    }
    const modifier = ((value - MEDIAN) / DIVISOR) | 0;
    return signed(modifier);
}
