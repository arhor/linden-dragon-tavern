import { render } from '@testing-library/react';
import About from '@/pages/About/About.component.jsx';

describe('Home.component.jsx', () => {
    test('renders without crashing', () => {
        render(<About />);
    });
});
