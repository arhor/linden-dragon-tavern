import React from 'react';

import ReactDOM from 'react-dom';

import App from '@/components/App';
import '@/config/i18n.js';
import '@/config/logging.js';

ReactDOM.render(
    <React.StrictMode>
        <App /> 
    </React.StrictMode>,
    document.getElementById('root'),
);
