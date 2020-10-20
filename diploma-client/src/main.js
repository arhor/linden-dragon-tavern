import Vue from 'vue';

import '@/assets/style/main.scss';
import '@/plugins/cookies.js';

import App from '@/App.vue';
import router from '@/routes';
import store from '@/store';
import vuetify from '@/plugins/vuetify';

Vue.config.productionTip = false;

window.appController = {
    main() {
        new Vue({
            vuetify,
            router,
            store,
            render: (h) => h(App),
        }).$mount('#app');
    },
};
