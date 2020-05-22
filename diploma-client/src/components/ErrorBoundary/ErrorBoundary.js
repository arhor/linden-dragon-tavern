import React from 'react';
import PropTypes from 'prop-types';
import StatelessContainer, { TYPE, SIZE } from '@/components/StatelessContainer';

class ErrorBoundary extends React.Component {
  static propTypes = {
    children: PropTypes.object.isRequired
  }

  state = {
    hasError: false
  }

  static getDerivedStateFromError(error) {
    return { hasError: true };
  }

  componentDidCatch(error, errorInfo) {

  }

  render() {
    return (
      <>
        {
          this.state.hasError
            ? <StatelessContainer
                type={TYPE.PAGE}
                size={SIZE.LARGE}
                title="Ups, something went wrong..."
                description="Please, contact system administrator if you have nothing else to do"
              />
            : this.props.children
        }
      </>
    );
  }
}

export default ErrorBoundary;