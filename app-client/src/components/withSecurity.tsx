import { ComponentType } from 'react';

import { computed } from 'mobx';
import { observer } from 'mobx-react';
import { Navigate } from 'react-router';

import StatelessWidget from '@/components/StatelessWidget';
import { useStore } from '@/store';

export default function withSecurity<T>(WrappedComponent: ComponentType<T>, authorities: string[]) {
    const SecuredComponent = (props: T) => {
        const { user } = useStore();

        if (user.authenticated) {
            const hasAccess = computed(() => authorities.every((it) => user.authorities.includes(it)));

            return hasAccess ? (
                <WrappedComponent {...props} />
            ) : (
                <StatelessWidget title="Access denied" description="You are not authorized to access this page." />
            );
        }
        return <Navigate to={{ pathname: '/login' }} />;
    };
    SecuredComponent.displayName = `withSecurity(${
        WrappedComponent.displayName || WrappedComponent.name || 'Component'
    })`;
    return observer(SecuredComponent);
}
