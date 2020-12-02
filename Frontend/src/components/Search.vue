<template>
  <div id="search-bar-container">
    <v-autocomplete
      class="search-bar"
      :items="this.items"
      prepend-inner-icon="mdi-map-search-outline"
      chips
      deletable-chips
      filled
      rounded
      shadow
      solo
      v-model="city"
      @change="getCity()"
    ></v-autocomplete>
  </div>
</template>

<script>
export default {
  name: "Search",
  data: () => ({
    items: ["Berlin", "Hamburg"],
    search: null,
    select: null,
    city: null,
  }),
  methods: {
    getCity: function () {
      this.$emit('change',this.city)
      alert(this.city);
      console.log(this.city);
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
.search-bar {
  /* -webkit-box-shadow: 4px 12px 31px -10px #cecece !important;
  box-shadow: 4px 12px 31px -10px #cecece !important; */
}
#search-icon {
  float: left;
  padding-top: 6px;
  padding-right: 6px;
}
</style>
