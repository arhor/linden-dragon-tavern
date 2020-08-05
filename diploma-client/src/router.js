import Vue from 'vue';
import VueRouter from 'vue-router';

import Home from '@/modules/home';

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home
    },
    {
        path: '/about',
        name: 'about',
        component: () => import('./views/about')
    },
    {
        path: '/monsters',
        name: 'monsters',
        component: () => import('@/modules/monsters')
    },
    {
        path: '/spells',
        name: 'spells',
        component: () => import('@/modules/spells')
    },
    {
        path: '/maps',
        name: 'maps',
        component: () => import('@/modules/maps')
    },
    {
        path: '/settings',
        name: 'settings',
        component: () => import('./views/Settings.vue')
    }
];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
});

export default router;
