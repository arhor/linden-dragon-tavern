import React from 'react';

import { render } from '@testing-library/react';

import About from '@/pages/About/About.jsx';

describe('Home.component.jsx', () => {
    test('renders without crashing', () => {
        render(<About />);
    });
});
