import styles from '@/components/MainAbilityCounter/MainAbilityCounter.module.css';
import { calculateModifier, minifyAbilityName } from '@/utils/abilityUtils';

export enum Ability {
    STR = 'Strength',
    DEX = 'Dexterity',
    CON = 'Constitution',
    INT = 'Intelligence',
    WIS = 'Wisdom',
    CHA = 'Charisma',
}

export type Props = {
    name: Ability;
    value: number;
};

const MainAbilityCounter = ({ name, value }: Props) => {
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
