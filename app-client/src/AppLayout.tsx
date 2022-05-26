import { ReactNode } from 'react';

import { observer } from 'mobx-react';

import Container from '@mui/material/Container';

import AppBreadcrumbs from '@/components/AppBreadcrumbs';
import AppNavBar from '@/components/AppNavBar';
import AppNotifier from '@/components/AppNotifier';

export type Props = {
    content: ReactNode
};

const AppLayout = ({ content }: Props) => (
    <>
        <AppNavBar />
        <AppNotifier />
        <Container component="main">
            <AppBreadcrumbs />
            {content}
        </Container>
    </>
);

export default observer(AppLayout);
