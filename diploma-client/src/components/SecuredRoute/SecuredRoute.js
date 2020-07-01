import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { isAuthenticated, getAuthorities } from '@/store/account';
import ErrorBoundary from '@/components/ErrorBoundary';
import StatelessContainer, { TYPE, SIZE } from '@/components/StatelessContainer';

const checkAuthorities = (authorities = [], requireAuthorities = []) => {
  if (authorities.length !== 0) {
    if (requireAuthorities.length === 0) {
      return true;
    }
    return requireAuthorities.some(auth => authorities.includes(auth));
  }
  return requireAuthorities.size === 0;
};

const SecuredRoute = ({ component, hasAuthorities = [], ...rest }) => {
  
  if (component === null || component === void 0) {
    throw new Error(`A component needs to be specified for private route for path ${rest.path}`);
  }

  const authenticated = useSelector(isAuthenticated);
  const authorities = useSelector(getAuthorities);

  const renderWithRedirect = props => {
    if (authenticated) {
      return checkAuthorities(authorities, hasAuthorities) ? (
        <ErrorBoundary>
          <component {...props} />
        </ErrorBoundary>
      ) : (
        <StatelessContainer
          type={TYPE.PAGE}
          size={SIZE.LARGE}
          title="Access is forbiden"
          description="You are not authorized to access this page."
        />
      );
    }
    return (
      <Redirect
        to={{
          pathname: '/login',
          search: props.location.search,
          state: { from: props.location },
        }}
      />
    );
  };

  return <Route {...rest} render={renderWithRedirect} />;
};

export default SecuredRoute;
