import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import CssBaseline from '@material-ui/core/CssBaseline';
import { ThemeProvider } from '@material-ui/core/styles';
import store from '@/store';
import App from '@/App';
import ErrorBoundary from '@/components/ErrorBoundary';
import config from '@/config';

ReactDOM.render(
  <React.StrictMode>
    <ThemeProvider theme={config.theme}>
      <CssBaseline />
      <ErrorBoundary>
        <Provider store={store}>
          <App />
        </Provider>
      </ErrorBoundary>
    </ThemeProvider>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
config.serviceWorker.unregister();