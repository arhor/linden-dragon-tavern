import PropTypes from 'prop-types';
import React from 'react';

import StatelessWidget, { SIZE, TYPE } from '@/components/StatelessWidget';

const DEFAULT_TITLE = 'Ups, something went wrong...';
const DEFAULT_DESCRIPTION = 'Please, contact system administrator if you have nothing else to do';

ErrorView.propTypes = {
    title: PropTypes.string,
    description: PropTypes.string,
};

function ErrorView({ title, description }) {
    return (
        <StatelessWidget
            type={TYPE.PAGE}
            size={SIZE.LARGE}
            title={title}
            description={description}
        />
    );
}

class ErrorBoundary extends React.Component {
    static propTypes = {
        children: PropTypes.object.isRequired,
    };

    /**
     * @type {{
     *  error: ?Error;
     *  errorInfo: ?React.ErrorInfo;
     * }}
     */
    state = {
        error: null,
        errorInfo: null,
    };

    /**
     * @param {Error} error
     */
    static getDerivedStateFromError(error) {
        return { error };
    }

    /**
     * @param {Error} error 
     * @param {React.ErrorInfo} errorInfo 
     */
    componentDidCatch(error, errorInfo) {
        this.setState({ error, errorInfo });
    }

    render() {
        const { error, errorInfo } = this.state;

        if (errorInfo) {
            const info = process.env.NODE_ENV === 'development'
                ? { title: error?.toString() ?? DEFAULT_TITLE, description: errorInfo.componentStack }
                : { title: DEFAULT_TITLE, description: DEFAULT_DESCRIPTION };
            return <ErrorView {...info} />;
        }
        return <>{this.props.children}</>;
    }
}

export default ErrorBoundary;
