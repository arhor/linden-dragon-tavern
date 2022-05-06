import React from 'react';

import { observer } from 'mobx-react';
import { Navigate } from 'react-router';

import StatelessWidget from '@/components/StatelessWidget';
import { useStore } from '@/store';

export default function withSecurity(Component, authorities = []) {
    const SecuredComponent = (props) => {
        const { user } = useStore();
        if (user.authenticated) {
            return user.hasAuthorities(authorities) ? (
                <Component {...props} />
            ) : (
                <StatelessWidget title="Access denied" description="You are not authorized to access this page." />
            );
        }
        return <Navigate to={{ pathname: '/login' }} />;
    };
    SecuredComponent.displayName = `Secured$${
        Component['displayName'] || Component['name'] || 'Component'
    }`;
    return observer(SecuredComponent);
}
