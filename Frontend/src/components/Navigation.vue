<template id="navBar">
  <div id="navigation">
    <v-navigation-drawer v-model="drawer" app>
      <v-list two-line>
        <img src="../../../images/amos_logo.jpg" style="width: 120px" id="logo">
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
    selectedItem: 1,
    items: [
      {
        text: "Home",
        icon: "mdi-home",
        link: "/",
      },
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
  text-decoration: none !important; /* no underline */
}
nav {
  border-radius: 30px;
  -webkit-box-shadow: 4px 12px 31px -5px #cecece;
  box-shadow: 4px 12px 31px -5px #cecece;
  margin-left: 15px;
}
.header {
  left: 295px !important;
  -webkit-box-shadow: 4px 12px 31px -10px #cecece !important;
  box-shadow: 4px 12px 31px -10px #cecece !important;
}
</style>

