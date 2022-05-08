import React from 'react';

import { render } from '@testing-library/react';

import About from '@/pages/About/About';

describe('About component', () => {
    test('should render without crashing', () => {
        render(<About />);
    });
});
