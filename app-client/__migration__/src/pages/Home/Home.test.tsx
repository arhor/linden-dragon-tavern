import React from 'react';

import { render } from '@testing-library/react';

import Home from '@/pages/Home/Home';

describe('Home component', () => {
    test('should render without crashing', () => {
        render(<Home />);
    });
});
