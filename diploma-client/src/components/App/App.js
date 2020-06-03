import React from 'react';
import { Provider } from 'react-redux';
import { ThemeProvider } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import ErrorBoundary from '@/components/ErrorBoundary';
import AppLayout from '@/components/AppLayout';
import store from '@/store';

const App = ({theme}) => {
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
};

export default App;