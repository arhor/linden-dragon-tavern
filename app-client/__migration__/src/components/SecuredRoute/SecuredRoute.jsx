import React from 'react';
import PropTypes from 'prop-types';
import { Route, Redirect } from 'react-router-dom';
import { observer } from 'mobx-react';

import ErrorBoundary from '@/components/ErrorBoundary';
import StatelessWidget, { TYPE, SIZE } from '@/components/StatelessWidget';
import { useStore } from '@/store';

SecuredRoute.propTypes = {
    Component: PropTypes.elementType.isRequired,
    authorities: PropTypes.arrayOf(PropTypes.string)
};

function SecuredRoute({ Component, authorities = [], ...rest }) {
    const { user } = useStore();

    return <Route {...rest} render={(props) => {
        if (user.authenticated) {
            return user.hasAuthorities(authorities) ? (
                <ErrorBoundary>
                    <Component {...props} />
                </ErrorBoundary>
            ) : (
                <StatelessWidget
                    type={TYPE.PAGE}
                    size={SIZE.LARGE}
                    title="Access is forbidden"
                    description="You are not authorized to access this page."
                />
            );
        } else {
            return (
                <Redirect
                    to={{
                        pathname: '/login',
                        search: props.location.search,
                        state: { from: props.location },
                    }}
                />
            );
        }
    }} />;
}

export default observer(SecuredRoute);
