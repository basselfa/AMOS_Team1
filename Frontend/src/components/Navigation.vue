<template id="navBar">
  <div id="navigation">
    <v-navigation-drawer v-model="drawer" app>
      <v-list two-line>
        <img src="../../static/amos_logo.jpg" style="width: 120px" id="logo">
          <v-list-item-group v-model="selectedItem" color="primary">
            <router-link v-for="(item, i) in items" :key="i" :to="item.link">
              <v-list-item v-on:click="selectedItem = +1">
                <v-list-item-icon>
                  <v-icon v-text="item.icon"></v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title v-text="item.text"></v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </router-link>
          </v-list-item-group>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app rounded class="rounded-pill header">
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <link
        href="https://cdn.jsdelivr.net/npm/@mdi/font@latest/css/materialdesignicons.min.css"
        rel="stylesheet"
      />

      <v-toolbar-title>Dashboard</v-toolbar-title>
    </v-app-bar>
  </div>
</template>

<script>
export default {
  name: "Navigation",
  data: () => ({
    drawer: null,
    selectedItem: 0,
    items: [
      {
        text: "Map",
        icon: "mdi-map",
        link: "/map",
      },
      {
        text: "Historization",
        icon: "mdi-poll-box",
        link: "/historization",
      },
      {
        text: "Configuration",
        icon: "mdi-cog",
        link: "/configuration",
      },
      {
        text: "About",
        icon: "mdi-information",
        link: "/about",
      },
    ],
  }),
};
</script>

<style>
#navBar {
  position: fixed;
  float: left;
  border-radius: 20px;
}
a:hover,
a:visited,
a:link,
a:active {
  color: black;
  text-decoration: none !important;
}

nav {
  border-radius: 30px;
  -webkit-box-shadow: 4px 6px 20px -10px #cecece !important;
  box-shadow: 2px 6px 20px -5px #cecece !important;
  margin-left: 15px;
}

.header {
  left: 15px;
  -webkit-box-shadow: 4px 6px 20px -10px #cecece !important;
  box-shadow: 2px 6px 20px -5px #cecece !important;
}

@media only screen and (min-width: 1270px) {
  .header {
    left: 295px !important;
  -webkit-box-shadow: 4px 6px 20px -10px #cecece !important;
  box-shadow: 2px 6px 20px -5px #cecece !important;
  }
}
</style>

