import { render } from '@testing-library/react';

import Loader from '@/components/Loader/Loader';

describe('Loader component', () => { 
    test('should render without crashing', () => {
        render(<Loader />);
    });
});

