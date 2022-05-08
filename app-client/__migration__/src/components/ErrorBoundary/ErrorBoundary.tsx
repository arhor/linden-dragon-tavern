import { Component, ErrorInfo, ReactNode } from 'react';

import StatelessWidget from '@/components/StatelessWidget';

const DEFAULT_TITLE = 'Ups, something went wrong...';
const DEFAULT_DESCRIPTION = 'Please, contact system administrator if you have nothing else to do';

type ErrorViewProps = {
    title?: string;
    description?: string;
};

const ErrorView = ({ title, description }: ErrorViewProps) => (
    <StatelessWidget
        type="page"
        size="large"
        title={title}
        description={description}
    />
);

type ErrorBoundaryProps = {
    children: ReactNode;
}

type ErrorBoundaryState = {
    error: Error | null;
    errorInfo: ErrorInfo | null;
};

class ErrorBoundary extends Component<ErrorBoundaryProps, ErrorBoundaryState> {
    state: ErrorBoundaryState = {
        error: null,
        errorInfo: null,
    };

    static getDerivedStateFromError(error: Error) {
        return { error };
    }

    componentDidCatch(error: Error, errorInfo: ErrorInfo) {
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
