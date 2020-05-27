import React from 'react';
import { Provider } from 'react-redux';
import CssBaseline from '@material-ui/core/CssBaseline';
import { ThemeProvider } from '@material-ui/core/styles';
import ErrorBoundary from '@/components/ErrorBoundary';
import Router from '@/components/Router';
import store from '@/store';

const App = ({theme}) => {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <ErrorBoundary>
        <Provider store={store}>
          <Router />
        </Provider>
      </ErrorBoundary>
    </ThemeProvider>
  );
};

export default App;