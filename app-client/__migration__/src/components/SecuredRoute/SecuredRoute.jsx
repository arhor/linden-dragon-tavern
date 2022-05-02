import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import { useSelector } from 'react-redux';

import ErrorBoundary from '@/components/ErrorBoundary';
import StatelessWidget, { TYPE, SIZE } from '@/components/StatelessWidget';
import { isAuthenticated, getAuthorities } from '@/store/user';

/**
 * @param {string[]} [authorities] 
 * @param {string[]} [requireAuthorities] 
 * @returns {boolean}
 */
const checkAuthorities = (authorities = [], requireAuthorities = []) => {
    if (authorities.length !== 0) {
        if (requireAuthorities.length === 0) {
            return true;
        }
        return requireAuthorities.some((auth) => authorities.includes(auth));
    }
    return requireAuthorities.length === 0;
};

function SecuredRoute({ component, authorities = [], ...rest }) {
    if (component === null || component === void 0) {
        throw new Error(
            `A component needs to be specified for private route for path ${rest.path}`
        );
    }

    const userAuthenticated = useSelector(isAuthenticated);
    const userAuthorities = useSelector(getAuthorities);

    const renderWithRedirect = (props) => {
        if (userAuthenticated) {
            return checkAuthorities(userAuthorities, authorities) ? (
                <ErrorBoundary>
                    {/* @ts-ignore */}
                    <component {...props} />
                </ErrorBoundary>
            ) : (
                <StatelessWidget
                    type={TYPE.PAGE}
                    size={SIZE.LARGE}
                    title="Access is forbidden"
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
