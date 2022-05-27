import { ReactNode } from 'react';

import { observer } from 'mobx-react';

import Container from '@mui/material/Container';

import AppBreadcrumbs from '@/components/AppBreadcrumbs';
import AppNavBar from '@/components/AppNavBar';
import AppNotifier from '@/components/AppNotifier';

export type Props = {
    children: ReactNode
};

const AppLayout = ({ children }: Props) => (
    <>
        <AppNavBar />
        <AppNotifier />
        <Container component="main">
            <AppBreadcrumbs />
            {children}
        </Container>
    </>
);

export default observer(AppLayout);
