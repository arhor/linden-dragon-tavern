import { StrictMode } from 'react';

import ReactDOM from 'react-dom';

import App from '@/App';
import '@/config/i18n';
import '@/config/logging';

ReactDOM.render(
    <StrictMode>
        <App />
    </StrictMode>,
    document.getElementById('root'),
);
