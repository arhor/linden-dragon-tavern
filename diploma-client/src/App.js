import React, { Suspense, lazy } from 'react';
import { BrowserRouter, Link, Switch, Route } from 'react-router-dom';
import Home from '@/routes/home';
import LangSelector from '@/components/LangSelector';
import Loader from '@/components/Loader';

const App = () => {
  return (
    <>
      <BrowserRouter>
        <Link to="/">Home</Link>
        <Link to="/about">About</Link>
        <Link to="/not_existing_path">To Not Found</Link>
        <main>
          <Suspense fallback={<Loader />}>
          <LangSelector />
            <Switch>
              <Route component={Home} path="/" exact />
              <Route component={lazy(() => import('@/routes/about'))} path="/about" />
              <Route component={lazy(() => import('@/components/NotFound'))} />
            </Switch>
          </Suspense>
        </main>
      </BrowserRouter>
    </>
  );
};

export default App;