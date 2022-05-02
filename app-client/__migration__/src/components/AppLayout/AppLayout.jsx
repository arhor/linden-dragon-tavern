import React from 'react';
import Container from '@mui/material/Container';
import { BrowserRouter } from 'react-router-dom';

import AppRouter from '@/components/AppRouter';
import NavBar from '@/components/NavBar';

function AppLayout() {
    return (
        <BrowserRouter>
            <NavBar />
            <Container component="main">
                <AppRouter />
            </Container>
        </BrowserRouter>
    );
}

export default AppLayout;