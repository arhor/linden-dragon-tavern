import React, {  } from 'react';
import { Navigate } from 'react-router';
import { observer } from 'mobx-react';

import StatelessWidget from '@/components/StatelessWidget';
import { useStore } from '@/store';

/**
 * @param {(props: any) => JSX.Element} Component 
 * @param {string[]} authorities 
 * @returns {(props: any) => JSX.Element}
 */
export default function withSecurity(Component, authorities = []) {
    /**
     * @param {any} props 
     * @returns {JSX.Element}
     */
    function SecuredComponent(props) {
        const { user } = useStore();
        if (user.authenticated) {
            return user.hasAuthorities(authorities) ? (
                <Component {...props} />
            ) : (
                <StatelessWidget title="Access denied" description="You are not authorized to access this page."/>
            );
        }
        return <Navigate to={{ pathname: '/login' }}/>;
    }
    SecuredComponent.displayName = `Secured$${
        Component['name'] ?? Component['displayName'] ?? 'Component'
    }`;
    return observer(SecuredComponent);
}
