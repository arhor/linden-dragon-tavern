import React from 'react';

import { SnackbarProvider } from 'notistack';
import { BrowserRouter } from 'react-router-dom';

import CssBaseline from '@mui/material/CssBaseline';
import { ThemeProvider } from '@mui/material/styles';

import AppLayout from '@/components/AppLayout';
import ErrorBoundary from '@/components/ErrorBoundary';
import { StoreProvider } from '@/store';
import theme from '@/theme';

function App() {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <ErrorBoundary>
                <StoreProvider>
                    <SnackbarProvider>
                        <BrowserRouter>
                            <AppLayout />
                        </BrowserRouter>
                    </SnackbarProvider>
                </StoreProvider>
            </ErrorBoundary>
        </ThemeProvider>
    );
}

export default App;
