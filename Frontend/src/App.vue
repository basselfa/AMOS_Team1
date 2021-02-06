<template>
  <div id="app">
    <v-app id="main">
        <navigation />
        <div class="page">
          <router-view/>
        </div>
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
  }
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

.page {
  padding-top: 75px;
}  
</style>
