import React from 'react';

import { BrowserRouter } from 'react-router-dom';

import CssBaseline from '@mui/material/CssBaseline';
import { ThemeProvider } from '@mui/material/styles';

import AppLayout from '@/components/AppLayout';
import ErrorBoundary from '@/components/ErrorBoundary';
import { store, StoreContext } from '@/store';
import theme from '@/theme';

function App() {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <ErrorBoundary>
                <StoreContext.Provider value={store}>
                    <BrowserRouter>
                        <AppLayout />
                    </BrowserRouter>
                </StoreContext.Provider>
            </ErrorBoundary>
        </ThemeProvider>
    );
}

export default App;
