import { SnackbarProvider } from 'notistack';

import CssBaseline from '@mui/material/CssBaseline';

import AppRouter from '@/AppRouter';
import AppThemeProvider from '@/AppThemeProvider';
import ErrorBoundary from '@/components/ErrorBoundary';
import { StoreProvider } from '@/store';

const App = () => (
    <AppThemeProvider>
        <CssBaseline />
        <ErrorBoundary>
            <StoreProvider>
                <SnackbarProvider>
                    <AppRouter />
                </SnackbarProvider>
            </StoreProvider>
        </ErrorBoundary>
    </AppThemeProvider>
);

export default App;
