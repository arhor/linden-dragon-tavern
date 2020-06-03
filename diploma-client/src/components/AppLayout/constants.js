import { lazy } from 'react';
import Home from '@/routes/home';

export const ROUTES = Object.freeze([
  {
    name: 'Home',
    path: '/',
    exact: true,
    component: Home,
  },
  {
    name: 'About',
    path: '/about',
    component: lazy(() => import('@/routes/about')),
  },
  {
    component: lazy(() => import('@/routes/not-found')),
  },
]);

export const LINKS = Object.freeze(ROUTES.filter(r => r.path !== null));