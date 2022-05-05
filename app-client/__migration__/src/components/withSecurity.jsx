import React from 'react';
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
    function SecuredComponent() {
        const { user } = useStore();
        if (user.authenticated) {
            return user.hasAuthorities(authorities) ? (
                <Component/>
            ) : (
                <StatelessWidget title="Access denied" description="You are not authorized to access this page."/>
            );
        }
        return <Navigate to={{ pathname: '/login' }}/>;
    }
    return observer(SecuredComponent);
}
