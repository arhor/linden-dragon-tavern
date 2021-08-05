import Vue from 'vue';
import VueRouter from 'vue-router';

import Home from '@/modules/home';
import sharedLib from '@/lib/diploma-shared.js';
import store from '@/store';
import {
    about,
    characters,
    home,
    maps,
    monsters,
    newCharacter,
    spells,
} from '@/routes/breadcrumbs.js';
import {
    composeGuards,
    createAuthoritiesGuard,
    createLangGuard,
    createLoginGuard,
} from '@/routes/guards.js';
import { loadLanguageAsync } from '@/plugins/i18n';

Vue.use(VueRouter);

const { Account } = sharedLib.org.arhor.diploma.Authorities;

const isLoggedIn = createLoginGuard(store);
const hasAuthorities = createAuthoritiesGuard(store);
const checkLang = createLangGuard(loadLanguageAsync);

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
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
            path: '/characters',
            name: 'characters',
            component: () => import('@/modules/characters'),
            meta: {
                breadcrumbs: [home, characters],
            },
            beforeEnter: composeGuards(checkLang),
        },
        {
            path: '/new-character',
            name: 'newCharacter',
            component: () => import('@/modules/characters/components/CharacterCreator'),
            meta: {
                breadcrumbs: [home, characters, newCharacter],
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
        },
    ],
});

export default router;
