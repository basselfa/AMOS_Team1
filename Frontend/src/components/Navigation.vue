<template>
  <div>
    <v-navigation-drawer v-model="drawer" app>
      <v-list two-line>
        <v-subheader>NAVIGATION</v-subheader>
        <v-list-item-group v-model="selectedItem" color="primary">
          <router-link v-for="(item, i) in items"
            :key="i" :to="item.link">
            <v-list-item

              v-on:click="selectedItem = +1"
            >

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

    <v-app-bar app>
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
        link: "/"
      },
      {
        text: "Map",
        icon: "mdi-map",
        link: "/map"
      },
      {
        text: "Historization",
        icon: "mdi-map",
        link: "/historization"
      },
      {
        text: "About",
        icon: "mdi-information",
        link: "/about"
      },
    ],
  }),
};
</script>

<style>
a:hover, a:visited, a:link, a:active{
  color: black;
  text-decoration: none !important; /* no underline */
}
</style>

