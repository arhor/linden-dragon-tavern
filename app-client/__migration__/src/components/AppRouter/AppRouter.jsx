import React, { lazy, Suspense } from 'react';
import { Route, Switch } from 'react-router-dom';
import Loader from '@/components/Loader';
import Home from '@/pages/Home';

const About = lazy(() => import('@/pages/About'));
const NotFound = lazy(() => import('@/pages/NotFound'));

function AppRouter() {
    return (
        <Suspense fallback={<Loader />}>
            <Switch>
                <Route component={Home} path="/" exact />
                <Route component={About} path="/about" />
                <Route component={NotFound} />
            </Switch>
        </Suspense>
    );
}

export default AppRouter;