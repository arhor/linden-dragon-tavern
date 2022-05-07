import React from 'react';

import Container from '@mui/material/Container';

import AppBreadcrumbs from '@/components/AppBreadcrumbs/AppBreadcrumbs.jsx';
import AppNavBar from '@/components/AppNavBar';
import AppNotifier from '@/components/AppNotifier';
import AppRouter from '@/components/AppRouter';

function AppLayout() {
    return (
        <>
            <AppNavBar />
            <AppNotifier />
            <Container component="main">
                <AppBreadcrumbs />
                <AppRouter />
            </Container>
        </>
    );
}

export default AppLayout;
