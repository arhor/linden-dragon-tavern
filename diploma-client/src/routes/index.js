import Vue from 'vue';
import VueRouter from 'vue-router';

import Home from '@/modules/home';
import breadcrumbs from '@/routes/breadcrumbs.js';

Vue.use(VueRouter);

const { about, spells, maps, monsters, home } = breadcrumbs;

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home,
        meta: {
            breadcrumbs: [home],
        },
    },
    {
        path: '/account',
        name: 'account',
        component: () => import('@/modules/auth'),
        meta: {
            breadcrumbs: [home],
        },
    },
    {
        path: '/about',
        name: 'about',
        component: () => import('@/modules/about'),
        meta: {
            breadcrumbs: [about],
        },
    },
    {
        path: '/monsters',
        name: 'monsters',
        component: () => import('@/modules/monsters'),
        meta: {
            breadcrumbs: [home, monsters],
        },
    },
    {
        path: '/spells',
        name: 'spells',
        component: () => import('@/modules/spells'),
        meta: {
            breadcrumbs: [home, spells],
        },
    },
    {
        path: '/maps',
        name: 'maps',
        component: () => import('@/modules/maps'),
        meta: {
            breadcrumbs: [home, maps],
        },
    },
    {
        path: '*',
        component: () => import('@/components/DndPageNotFound.vue'),
    },
];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes,
});

export default router;
