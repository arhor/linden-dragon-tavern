import React, { Suspense } from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import Container from '@material-ui/core/Container';
import NavBar from '@/components/NavBar';
import LangSelector from '@/components/LangSelector';
import Loader from '@/components/Loader';
import { ROUTES } from '@/components/AppLayout/constants';

const AppLayout = () => {
  return (
    <BrowserRouter>
      <NavBar />
      <Container component='main'>
        <Suspense fallback={<Loader />}>
          <LangSelector />
          <Switch>
            {ROUTES.map(route => <Route {...route} />)}
          </Switch>
        </Suspense>
      </Container>
    </BrowserRouter>
  );
};

export default AppLayout;