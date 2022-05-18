import { render } from '@testing-library/react';

import NotFound from '@/pages/NotFound/NotFound';

describe('NotFound component', () => {
    test('should render without crashing', () => {
        render(<NotFound />);
    });
});
