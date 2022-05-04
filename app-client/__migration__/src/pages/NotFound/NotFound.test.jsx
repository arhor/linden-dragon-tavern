import React from 'react';
import { render } from '@testing-library/react';

import NotFound from '@/pages/NotFound/NotFound.jsx';

describe('NotFound.component.jsx', () => {
    test('renders without crashing', () => {
        render(<NotFound />);
    });
});
