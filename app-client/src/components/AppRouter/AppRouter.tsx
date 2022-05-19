import React, { lazy, Suspense } from 'react';

import { Route, Routes } from 'react-router';

import Loader from '@/components/Loader';

const Home = lazy(() => import('@/pages/Home'));
const About = lazy(() => import('@/pages/About'));
const SignIn = lazy(() => import('@/pages/SignIn'));
const SignUp = lazy(() => import('@/pages/SignUp'));
const NotFound = lazy(() => import('@/pages/NotFound'));

const AppRouter = () => (
    <Suspense fallback={<Loader />}>
        <Routes>
            {/* @formatter:off */}
            <Route path="/"        element={<Home />} />
            <Route path="/about"   element={<About />} />
            <Route path="/sign-in" element={<SignIn />} />
            <Route path="/sign-up" element={<SignUp />} />
            <Route path="*"        element={<NotFound />} />
            {/* @formatter:on */}
        </Routes>
    </Suspense>
);

export default AppRouter;
