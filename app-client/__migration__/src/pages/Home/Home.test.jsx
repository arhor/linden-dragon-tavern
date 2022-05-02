import { render } from '@testing-library/react';
import Home from '@/pages/Home/Home.component.jsx';

describe('Home.component.jsx', () => {
    test('renders without crashing', () => {
        render(<Home />);
    });
});
