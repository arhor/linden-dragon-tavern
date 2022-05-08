import React from 'react';

import StatelessWidget from '@/components/StatelessWidget';

const DEFAULT_TITLE = 'Ups, something went wrong...';
const DEFAULT_DESCRIPTION = 'Please, contact system administrator if you have nothing else to do';

const ErrorView: React.FC<{
    title?: string;
    description?: string;
}> = ({ title, description }) => (
    <StatelessWidget
        type="page"
        size="large"
        title={title}
        description={description}
    />
);

class ErrorBoundary extends React.Component<{}, {
    error: Error | null;
    errorInfo: React.ErrorInfo | null;
}> {
    static getDerivedStateFromError(error: Error) {
        return { error };
    }

    componentDidCatch(error: Error, errorInfo: React.ErrorInfo) {
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
        return this.props.children;
    }
}

export default ErrorBoundary;
