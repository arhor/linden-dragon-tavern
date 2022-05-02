import React from 'react';
import PropTypes from 'prop-types';
import { calculateModifier, minifyAbilityName } from '@/utils/abilityUtils.js';
import styles from '@/components/MainAbilityCounter/MainAbilityCounter.module.css';

/**
 * @readonly
 * @enum {string}
 */
 export const ABILITY = {
    STR: 'Strength',
    DEX: 'Dexterity',
    CON: 'Constitution',
    INT: 'Intelligence',
    WIS: 'Wisdom',
    CHA: 'Charisma',
};

MainAbilityCounter.propTypes = {
    name: PropTypes.oneOf(Object.values(ABILITY)).isRequired,
    value: PropTypes.number.isRequired,
};

function MainAbilityCounter({ name, value }) {
    const typeDisplayName = minifyAbilityName(name);
    const modifier = calculateModifier(value);

    return (
        <div className={styles.counterContainer} data-testid="counter-container">
            <div className={styles.counterModifier} data-testid="counter-modifier">
                {modifier}
            </div>
            <span className={styles.counterName} data-testid="counter-name">
                {typeDisplayName}
            </span>
            <span className={styles.counterValue} data-testid="counter-value">
                {value}
            </span>
        </div>
    );
}

export default MainAbilityCounter;
