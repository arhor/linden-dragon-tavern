import { ComponentType } from 'react';

import { MemoryRouter } from 'react-router';

export function withMemoryRouter<T>(Component: ComponentType<T>) {
    const WrappedComponent = (props: T) => (
        <MemoryRouter>
            <Component {...props} />
        </MemoryRouter>
    );
    WrappedComponent.displayName = `withMemoryRouter(${
        Component.displayName || Component.name || 'Component'
    })`;
    return WrappedComponent;
}
