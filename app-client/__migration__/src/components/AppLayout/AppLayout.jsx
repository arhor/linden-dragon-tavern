import React from 'react';
import Container from '@mui/material/Container';
import { BrowserRouter } from 'react-router-dom';

import AppBreadcrumbs from '@/components/AppBreadcrumbs/AppBreadcrumbs.jsx';
import AppRouterView from '@/components/AppRouterView';
import NavBar from '@/components/NavBar';

function AppLayout() {
    return (
        <BrowserRouter>
            <NavBar />
            <Container component="main">
                <AppBreadcrumbs />
                <AppRouterView />
            </Container>
        </BrowserRouter>
    );
}

export default AppLayout;
