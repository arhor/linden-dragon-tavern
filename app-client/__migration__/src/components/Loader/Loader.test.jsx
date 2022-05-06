import React from 'react';

import { render } from '@testing-library/react';

import Loader from '@/components/Loader/Loader.jsx';

describe('Loader component', () => { 
    test('should render without crashing', () => {
        render(<Loader />);
    });
});

