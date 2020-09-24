import Vue from 'vue';
import App from '@/App.vue';
import router from '@/router';
import store from '@/store';
import vuetify from '@/plugins/vuetify';
import '@/assets/style/main.scss';

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
