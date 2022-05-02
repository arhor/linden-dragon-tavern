import CssBaseline from '@mui/material/CssBaseline';
import React from 'react';
import { Provider } from 'react-redux';
import { ThemeProvider } from '@mui/material/styles';

import AppLayout from '@/components/AppLayout';
import ErrorBoundary from '@/components/ErrorBoundary';
import store from '@/store';
import theme from '@/theme.js';

function App() {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <ErrorBoundary>
                <Provider store={store}>
                    <AppLayout />
                </Provider>
            </ErrorBoundary>
        </ThemeProvider>
    );
}

export default App;
