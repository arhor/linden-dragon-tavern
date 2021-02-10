import Vue from 'vue';
import VueRouter from 'vue-router';

import Home from '@/modules/home';
import sharedLib from '@/lib/diploma-shared.js';
import store from '@/store';

import { about, home, maps, monsters, spells } from '@/routes/breadcrumbs.js';
import {
    composeGuards,
    createAuthoritiesGuard,
    createLangGuard,
    createLoginGuard,
} from '@/routes/guards.js';

Vue.use(VueRouter);

const { Account } = sharedLib.org.arhor.diploma.Authorities;

const isLoggedIn = createLoginGuard(store);
const hasAuthorities = createAuthoritiesGuard(store);
const checkLang = createLangGuard();

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home,
        meta: {
            breadcrumbs: [home],
        },
        beforeEnter: composeGuards(checkLang),
    },
    {
        path: '/account',
        name: 'account',
        component: () => import('@/modules/auth'),
        meta: {
            breadcrumbs: [home],
        },
        beforeEnter: composeGuards(isLoggedIn, hasAuthorities(Account.VIEW), checkLang),
    },
    {
        path: '/about',
        name: 'about',
        component: () => import('@/modules/about'),
        meta: {
            breadcrumbs: [about],
        },
        beforeEnter: composeGuards(checkLang),
    },
    {
        path: '/monsters',
        name: 'monsters',
        component: () => import('@/modules/monsters'),
        meta: {
            breadcrumbs: [home, monsters],
        },
        beforeEnter: composeGuards(checkLang),
    },
    {
        path: '/spells',
        name: 'spells',
        component: () => import('@/modules/spells'),
        meta: {
            breadcrumbs: [home, spells],
        },
        beforeEnter: composeGuards(checkLang),
    },
    {
        path: '/maps',
        name: 'maps',
        component: () => import('@/modules/maps'),
        meta: {
            breadcrumbs: [home, maps],
        },
        beforeEnter: composeGuards(checkLang),
    },
    {
        path: '*',
        component: () => import('@/components/DndPageNotFound.vue'),
        beforeEnter: composeGuards(checkLang),
    },
];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes,
});

export default router;
