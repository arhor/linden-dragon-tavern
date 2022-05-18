import { render, screen } from '@testing-library/react';

import MainAbilityCounter, { Ability } from '@/components/MainAbilityCounter/MainAbilityCounter';

describe('MainAbilityCounter component', () => {
    test('should render without crashing', () => {
        render(<MainAbilityCounter name={Ability.CHA} value={30}/>);
    });

    test('should display correct ability name and modifier', () => {
        render(<MainAbilityCounter name={Ability.STR} value={20} />);

        expect(screen.getByTestId('counter-name')).toHaveTextContent('STR');
        expect(screen.getByTestId('counter-value')).toHaveTextContent('20');
        expect(screen.getByTestId('counter-modifier')).toHaveTextContent('+5');
    });
}); 
