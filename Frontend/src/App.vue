<template>
  <div id="app">
    <v-app id="main">
      <div style="margin-left: 0">
        <!--- loading indicator will be needed when waiting for the data to visualize 
        <v-progress-circular
          indeterminate
          color="primary"
        ></v-progress-circular>
        <p>Loading map</p>
        --->
        <iframe
          width="800"
          height="550"
          frameborder="0"
          scrolling="no"
          marginheight="0"
          marginwidth="0"
          src="https://www.openstreetmap.org/export/embed.html?bbox=13.317360877990724%2C52.50269091804005%2C13.372292518615724%2C52.52611754877629&amp;layer=mapnik"
          style="border: 1px solid black; margin-top: 10%"
        ></iframe>
      </div>

      <v-navigation-drawer v-model="drawer" app>
        <v-list two-line>
          <v-subheader>NAVIGATION</v-subheader>
          <v-list-item-group v-model="selectedItem" color="primary">
            <v-list-item
              v-for="(item, i) in items"
              :key="i"
              v-on:click="selectedItem = +1"
            >
              <v-list-item-icon>
                <v-icon v-text="item.icon"></v-icon>
              </v-list-item-icon>
              <v-list-item-content>
                <v-list-item-title v-text="item.text"></v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-item-group>
        </v-list>
      </v-navigation-drawer>

      <v-app-bar app>
        <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
        <link
          href="https://cdn.jsdelivr.net/npm/@mdi/font@latest/css/materialdesignicons.min.css"
          rel="stylesheet"
        />

        <v-toolbar-title>Dashboard</v-toolbar-title>
      </v-app-bar>
    </v-app>

    <!---
    <img src="./assets/logo.png">
    <router-view />
    -->
  </div>
</template>

<script>
export default {
  name: "App",
  data: () => ({
    drawer: null,
    selectedItem: 1,
    items: [
      {
        text: "Home",
        icon: "mdi-home",
      },
      {
        text: "Map",
        icon: "mdi-map",
      },
      {
        text: "About",
        icon: "mdi-information",
      },
    ],
  }),
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
