import React from 'react';
import ReactDOM from 'react-dom';
import ErrorBoundary from './ErrorBoundary.js';

describe('ErrorBoundary tests', () => {
    test('renders without crashing', () => {
        const div = document.createElement('div');

        ReactDOM.render(
            <ErrorBoundary>
                <p>simple text block</p>
            </ErrorBoundary>,
            div
        );

        ReactDOM.unmountComponentAtNode(div);
    });
});
