import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '@/modules/home';

Vue.use(VueRouter);

const breadcrumb = {
    home: {
        text: 'Home',
        to: '/',
    },
    about: {
        text: 'About',
        to: '/about',
    },
    monsters: {
        text: 'Monsters',
        to: '/monsters',
    },
    spells: {
        text: 'Spells',
        to: '/spells',
    },
    maps: {
        text: 'Maps',
        to: '/maps',
    },
};

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home,
        meta: {
            breadcrumbs: [breadcrumb.home],
        },
    },
    {
        path: '/about',
        name: 'about',
        component: () => import('@/modules/about'),
        meta: {
            breadcrumbs: [breadcrumb.about],
        },
    },
    {
        path: '/monsters',
        name: 'monsters',
        component: () => import('@/modules/monsters'),
        meta: {
            breadcrumbs: [breadcrumb.home, breadcrumb.monsters],
        },
    },
    {
        path: '/spells',
        name: 'spells',
        component: () => import('@/modules/spells'),
        meta: {
            breadcrumbs: [breadcrumb.home, breadcrumb.spells],
        },
    },
    {
        path: '/maps',
        name: 'maps',
        component: () => import('@/modules/maps'),
        meta: {
            breadcrumbs: [breadcrumb.home, breadcrumb.maps],
        },
    },
];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes,
});

export default router;
