<template>
  <div id="search-bar-container">
    <v-autocomplete :items="this.items" prepend-inner-icon="mdi-map-search-outline" chips deletable-chips filled rounded solo></v-autocomplete>
  </div>
</template>

<script>
export default {
  name: "Search",
    data: () => ({
    items: ['Berlin','Hamburg'],
    search: null,
    select: null,
  }),
  methods: {
    getCity: function (city) {
      alert(city);
      console.log(city);
    },
  },
};
</script>

<style>
#search-bar-container {
  padding-top: 4%;
  padding-left: 20%;
  padding-right: 20%;
}
#search-icon {
  float: left;
  padding-top: 6px;
  padding-right: 6px;
}
</style>
