import React, { lazy, Suspense } from 'react';

import { BrowserRouter as Router, Outlet, Route, Routes } from 'react-router-dom';

import AppLayout from '@/AppLayout';
import Loader from '@/components/Loader';
import Home from '@/pages/Home';
import NotFound from '@/pages/NotFound';
import SignIn from '@/pages/SignIn';
import SignUp from '@/pages/SignUp';

const About = lazy(() => import('@/pages/About'));

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
