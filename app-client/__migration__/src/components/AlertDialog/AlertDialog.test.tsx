import { render } from '@testing-library/react';

import AlertDialog from '@/components/AlertDialog/AlertDialog';

describe('AlertDialog component', () => {
    test('should render without crashing', () => {
        render(<AlertDialog dialogProps={{ open: true }} contentText="" />);
    });
});
