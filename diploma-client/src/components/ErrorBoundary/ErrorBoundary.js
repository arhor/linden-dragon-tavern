import React from 'react';
import StatelessContainer, { TYPE, SIZE } from '@/components/StatelessContainer';

// TODO: implement popup window for not critical errors
class ErrorBoundary extends React.Component {

  state = {
    hasError: false
  }

  static getDerivedStateFromError(error) {
    return { hasError: true };
  }

  componentDidCatch(error, errorInfo) {

  }

  errorView = () => {
    return (
      <StatelessContainer
        type={TYPE.PAGE}
        size={SIZE.LARGE}
        title="Ups, something went wrong..."
        description="Please, contact system administrator if you have nothing else to do"
      />
    );
  }

  render() {
    return this.state.hasError
      ? this.errorView()
      : this.props.children;
  }
}

export default ErrorBoundary;