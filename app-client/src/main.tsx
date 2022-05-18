import React from 'react';

import ReactDOM from 'react-dom';

import App from '@/App';
import '@/config/i18n';
import '@/config/logging';

ReactDOM.render(
    <React.StrictMode>
        <App />
    </React.StrictMode>,
    document.getElementById('root'),
);
