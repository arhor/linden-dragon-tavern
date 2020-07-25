import React, { Suspense, lazy } from 'react';
import { Switch, Route } from 'react-router-dom';
import LangSelector from '@/components/LangSelector';
import Loader from '@/components/Loader';
import Home from '@/routes/home';
import SecuredRoute from '@/components/SecuredRoute';
import lib from '@/lib/diploma-shared';

const { Account } = lib.org.arhor.diploma.Authorities;

/* prettier-ignore */
const AppRouter = () => {
    return (
        <Suspense fallback={<Loader />}>
            <LangSelector />
            <Switch>
                <Route
                    path="/"
                    component={Home}
                    exact
                />
                <Route
                    path="/about"
                    component={lazy(() => import('@/routes/about'))}
                />
                <SecuredRoute
                    path="/account"
                    component={lazy(() => import('@/routes/account'))}
                    hasAuthorities={[Account.VIEW]}
                />
                <Route component={lazy(() => import('@/routes/not-found'))} />
            </Switch>
        </Suspense>
    );
};

export default AppRouter;
