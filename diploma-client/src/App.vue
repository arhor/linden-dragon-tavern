<template>
  <v-app>
    <v-navigation-drawer
      app
      absolute
      overflow
      temporary
      v-model="drawer"
    />

    <v-app-bar app dark fixed>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"/>
      <v-toolbar-title>Diploma app</v-toolbar-title>
    </v-app-bar>

    <v-content>
      <v-container fluid>
        <router-view/>
      </v-container>
    </v-content>
    
    <v-footer app dark fixed>
      <v-spacer/>
      <span class="px-3">Maksim Buryshynets &copy; {{ new Date().getFullYear() }}</span>
    </v-footer>
  </v-app>
</template>

<script>
import http from '@/util/http.js'

export default {
  name: 'App',
  components: {
  },
  data() {
    return {
      drawer: false,
    }
  },
  computed: {
  },
  methods: {
  },
  beforeCreate() {
    console.log(this)

    http.post('/api/v1/users', 'Hello from the Client!')
        .then(res => console.log(res))
        .catch(err => console.error(err))
  }
}
</script>

<style>

</style>
