import Vue from 'vue';
import VueCookies from 'vue-cookies';

import sharedLib from '@/lib/diploma-shared.js';
import { CSRF_TOKEN } from '@/api/interceptors/addCsrfToken';

const { CsrfUtils } = sharedLib.org.arhor.diploma;

Vue.use(VueCookies);

Vue.$cookies.set(CsrfUtils.CSRF_COOKIE_NAME, CSRF_TOKEN);
