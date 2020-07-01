import React from 'react';
import StatelessContainer, { TYPE, SIZE } from '@/components/StatelessContainer';

const DEFAULT_TITLE = 'Ups, something went wrong...';
const DEFAULT_DESCRIPTION = 'Please, contact system administrator if you have nothing else to do';

const ErrorView = ({title, description}) => {
  return (
    <StatelessContainer
      type={TYPE.PAGE}
      size={SIZE.LARGE}
      title={title}
      description={description}
    />
  );
};

class ErrorBoundary extends React.Component {
  state = {
    error: null,
    errorInfo: null,
  }

  static getDerivedStateFromError(error) {
    return { error };
  }

  componentDidCatch(error, errorInfo) {
    this.setState({ error, errorInfo });
  }

  render() {
    const { error, errorInfo } = this.state;

    if (errorInfo) {

      const info = process.env.NODE_ENV === 'development' ? {
        title: error?.toString() ?? DEFAULT_TITLE,
        description: errorInfo.componentStack,
      } : {
        title: DEFAULT_TITLE,
        description: DEFAULT_DESCRIPTION,
      }
      
      return (
        <ErrorView {...info} />
      );
    }

    return this.props.children;
  }
}

export default ErrorBoundary;