import React from 'react';
import { render, screen } from '@testing-library/react';
import ActionList from '@/components/ActionList/ActionList.jsx';

describe('ActionList component', () => {
    test('should render without crashing', () => {
        render(<ActionList />);
    });

    test('should render actions correctly', () => {
        // given
        const actions = [
            { name: 'Name 1', desc: 'Description 1' },
            { name: 'Name 2', desc: 'Description 2' },
            { name: 'Name 3', desc: 'Description 3' },
        ];
        const expectedContent = actions.map((it) => `${it.name}: ${it.desc}`).join(', ');

        // when
        render(<ActionList actions={actions} />);

        // then
        expect(screen.getByTestId('action-list-container')).toHaveTextContent(expectedContent);
    });
});
