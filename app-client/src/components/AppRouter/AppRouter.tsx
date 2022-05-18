import React, { lazy, Suspense } from 'react';

import { Route, Routes } from 'react-router';

import Loader from '@/components/Loader';

const Home = lazy(() => import('@/pages/Home'));
const About = lazy(() => import('@/pages/About'));
const NotFound = lazy(() => import('@/pages/NotFound'));

const AppRouter = () => (
    <Suspense fallback={<Loader />}>
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/about" element={<About />} />
            <Route path="*" element={<NotFound />} />
        </Routes>
    </Suspense>
);

export default AppRouter;
