import Vue from 'vue';
import VueRouter from 'vue-router';

import Home from '@/modules/home';

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home,
        children: [], // TODO: implement nested routes
        meta: {
            breadcrumbs: [{ text: 'Home', to: '/', disabled: true }]
        }
    },
    {
        path: '/about',
        name: 'about',
        component: () => import('./views/about'),
        meta: {
            breadcrumbs: [{ text: 'About', to: '/about', disabled: true }]
        }
    },
    {
        path: '/monsters',
        name: 'monsters',
        component: () => import('@/modules/monsters'),
        meta: {
            breadcrumbs: [
                { text: 'Home', to: '/', disabled: false },
                { text: 'Monsters', to: '/monsters', disabled: true }
            ]
        }
    },
    {
        path: '/spells',
        name: 'spells',
        component: () => import('@/modules/spells'),
        meta: {
            breadcrumbs: [
                { text: 'Home', to: '/', disabled: false },
                { text: 'Spells', to: '/spells', disabled: true }
            ]
        }
    },
    {
        path: '/maps',
        name: 'maps',
        component: () => import('@/modules/maps'),
        meta: {
            breadcrumbs: [
                { text: 'Home', to: '/', disabled: false },
                { text: 'Maps', to: '/maps', disabled: true }
            ]
        }
    },
    {
        path: '/settings',
        name: 'settings',
        component: () => import('@/views/Settings.vue'),
        meta: {
            breadcrumbs: [{ text: 'Settings', to: '/settings', disabled: true }]
        }
    }
];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
});

export default router;
