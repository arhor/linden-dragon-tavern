import React, { Suspense, lazy } from 'react';
import { BrowserRouter, Link, Switch, Route } from 'react-router-dom';
import Home from '@/routes/home';
import LangSelector from '@/components/LangSelector';
import Loader from '@/components/Loader';

const Router = () => {
  return (
    <BrowserRouter>
      <Link to="/">Home</Link>
      <Link to="/about">About</Link>
      <main>
        <Suspense fallback={<Loader />}>
          <LangSelector />
          <Switch>

            <Route
              exact
              path="/"
              component={Home}
            />

            <Route
              path="/about"
              component={lazy(() => import('@/routes/about'))}
            />

            <Route
              component={lazy(() => import('@/components/NotFound'))}
            />

          </Switch>
        </Suspense>
      </main>
    </BrowserRouter>
  );
};

export default Router;