<template>
<div id="search-bar-container">
  <v-text-field id="search-bar"
            label="Solo"
            placeholder="Search for a city e.g. Berlin"
            solo
          ></v-text-field>
</div>
</template>

<script>
export default {
name: "Search",
}
</script>

<style>
#search-bar-container{
  padding-top: 4%;
  padding-left: 20%;
  padding-right: 20%;
}

</style>
