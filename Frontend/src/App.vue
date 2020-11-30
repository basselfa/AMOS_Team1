<template>
  <div id="app">
    <v-app id="main">
      <v-row>
        <v-col>
          <router-link to="/historization">historization</router-link>
          <navigation />
          <router-view/>
        </v-col>
      </v-row>
    </v-app>
  </div>
</template>

<script>
import axios from "axios";

import Navigation from "./components/Navigation.vue";
import router from './router'

export default {
  name: "App",
  router,
  components: {
    Navigation
  },

};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
