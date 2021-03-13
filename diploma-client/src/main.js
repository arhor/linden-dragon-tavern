import Vue from 'vue';
import VueCompositionAPI from '@vue/composition-api';

import '@/assets/style/main.scss';
import '@/plugins/cookies.js';

import App from '@/App.vue';
import i18n from '@/plugins/i18n.js';
import router from '@/routes';
import store from '@/store';
import vuetify from '@/plugins/vuetify.js';

Vue.use(VueCompositionAPI);
Vue.config.productionTip = false;

window.appController = {
    main() {
        new Vue({
            i18n,
            vuetify,
            router,
            store,
            render: (h) => h(App),
        }).$mount('#app');
    },
};
