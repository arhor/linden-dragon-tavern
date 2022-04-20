import Vue from 'vue';
import VueCookies from 'vue-cookies';

import { CSRF_TOKEN } from '@/api/interceptors/addCsrfToken';

Vue.use(VueCookies);

Vue['$cookies'].set('csrf-token', CSRF_TOKEN);
