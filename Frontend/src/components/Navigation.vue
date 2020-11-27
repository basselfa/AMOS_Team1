<template>
  <div>
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