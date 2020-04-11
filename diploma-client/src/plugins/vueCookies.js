import Vue from 'vue';
import VueCookies from 'vue-cookies';
import { csrfToken } from '@/api/csrfToken';

Vue.use(VueCookies);

Vue.$cookies.set('csrf-token', csrfToken);
