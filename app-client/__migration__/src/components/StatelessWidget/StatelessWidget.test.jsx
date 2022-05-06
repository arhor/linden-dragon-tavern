import React from 'react';

import { render } from '@testing-library/react';

import StatelessWidget from '@/components/StatelessWidget/StatelessWidget.jsx';

describe('StatelessWidget component', () => {
    test('should render without crashing', () => {
        render(<StatelessWidget />);
    });
});
