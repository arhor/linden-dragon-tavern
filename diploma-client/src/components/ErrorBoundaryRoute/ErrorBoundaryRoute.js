import React from 'react';
import { Route } from 'react-router-dom';
import ErrorBoundary from '@/components/ErrorBoundary';

export const ErrorBoundaryRoute = ({ component, ...rest }) => {
    if (!component) {
        throw new Error(`A component needs to be specified for path ${rest.path}`);
    }

    const encloseInErrorBoundary = (props) => (
        <ErrorBoundary>
            <component {...props} />
        </ErrorBoundary>
    );

    return <Route {...rest} render={encloseInErrorBoundary} />;
};

export default ErrorBoundaryRoute;
