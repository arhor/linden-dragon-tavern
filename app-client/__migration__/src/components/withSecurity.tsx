import React from 'react';

import { observer } from 'mobx-react';
import { Navigate } from 'react-router';

import StatelessWidget from '@/components/StatelessWidget';
import { useStore } from '@/store';

export default function withSecurity<T>(WrappedComponent: React.ComponentType<T>, authorities: string[]) {
    const SecuredComponent = (props: T) => {
        const { user } = useStore();
        if (user.authenticated) {
            return user.hasAuthorities(authorities) ? (
                <WrappedComponent {...props} />
            ) : (
                <StatelessWidget title="Access denied" description="You are not authorized to access this page." />
            );
        }
        return <Navigate to={{ pathname: '/login' }} />;
    };
    SecuredComponent.displayName = `Secured$${
        WrappedComponent.displayName || WrappedComponent.name || 'Component'
    }`;
    return observer(SecuredComponent);
}
