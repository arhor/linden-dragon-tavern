import React from 'react';

import { render, screen } from '@testing-library/react';

import MainAbilityCounter, { ABILITY } from '@/components/MainAbilityCounter/MainAbilityCounter.jsx';

describe('MainAbilityCounter component', () => {
    test('should render without crashing', () => {
        render(<MainAbilityCounter name={ABILITY.CHA} value={30}/>);
    });

    test('should display correct ability name and modifier', () => {
        render(<MainAbilityCounter name={ABILITY.STR} value={20} />);

        expect(screen.getByTestId('counter-name')).toHaveTextContent('STR');
        expect(screen.getByTestId('counter-value')).toHaveTextContent('20');
        expect(screen.getByTestId('counter-modifier')).toHaveTextContent('+5');
    });
}); 
