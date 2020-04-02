import Vue from 'vue'
import App from '@/App.vue'
import router from '@/router'
import store from '@/store'
import vuetify from '@/plugins/vuetify'
import filters from '@/filters'
import '@/plugins/vueCookies'

for (const [name, filter] of Object.entries(filters)) {
  Vue.filter(name, filter)
}

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
