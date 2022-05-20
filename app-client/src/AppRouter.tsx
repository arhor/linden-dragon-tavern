import React, { lazy, Suspense } from 'react';

import { BrowserRouter as Router, Outlet, Route, Routes } from 'react-router-dom';

import AppLayout from '@/AppLayout';
import Loader from '@/components/Loader';
import Home from '@/pages/Home';
import NotFound from '@/pages/NotFound';

const About = lazy(() => import('@/pages/About'));
const SignIn = lazy(() => import('@/pages/SignIn'));
const SignUp = lazy(() => import('@/pages/SignUp'));

const AppRouter = () => (
    <Router>
        <Suspense fallback={<Loader />}>
            <Routes>
                <Route path="/" element={<AppLayout content={<Outlet />} />}>
                    <Route index element={<Home />} />
                    <Route path="/about" element={<About />} />
                    <Route path="/sign-in" element={<SignIn />} />
                    <Route path="/sign-up" element={<SignUp />} />
                </Route>
                <Route path="*" element={<NotFound />} />
            </Routes>
        </Suspense>
    </Router>
);

export default AppRouter;
