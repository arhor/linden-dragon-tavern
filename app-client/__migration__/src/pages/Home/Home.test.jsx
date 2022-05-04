import React from 'react';
import { render } from '@testing-library/react';

import Home from '@/pages/Home/Home.jsx';

describe('Home.component.jsx', () => {
    test('renders without crashing', () => {
        render(<Home />);
    });
});
