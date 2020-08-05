import 'fontsource-roboto';
import '@mdi/font/css/materialdesignicons.css';

import Vue from 'vue';
import Vuetify from 'vuetify/lib';

Vue.use(Vuetify, {
    components: {}
});

export default new Vuetify({
    icons: {
        iconfont: 'mdi'
    }
});
