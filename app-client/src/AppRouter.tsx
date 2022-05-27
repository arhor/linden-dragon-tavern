import { lazy, Suspense } from 'react';

import { BrowserRouter as Router, Outlet, Route, Routes } from 'react-router-dom';

import AppLayout from '@/AppLayout';
import Loader from '@/components/Loader';
import Home from '@/pages/Home';
import NotFound from '@/pages/NotFound';
import SignIn from '@/pages/SignIn';
import SignUp from '@/pages/SignUp';
import { store } from '@/store';
import { withInit } from '@/utils/react-utils';

const About = lazy(() => import('@/pages/About'));
const Creatures = lazy(withInit(() => import('@/pages/Creatures'), [store.creature.load]));

const AppRouter = () => (
    <Router>
        <Routes>
            <Route
                path="/"
                element={
                    <AppLayout>
                        <Suspense fallback={<Loader />}>
                            <Outlet />
                        </Suspense>
                    </AppLayout>
                }
            >
                <Route index element={<Home />} />
                <Route path="/about" element={<About />} />
                <Route path="/creatures" element={<Creatures />} />
                <Route path="/sign-in" element={<SignIn />} />
                <Route path="/sign-up" element={<SignUp />} />
            </Route>
            <Route path="*" element={<NotFound />} />
        </Routes>
    </Router>
);

export default AppRouter;
