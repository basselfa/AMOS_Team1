<template>
  <div id="search-bar-container">
    <v-row>
      <v-col cols="12" sm="6" md="8">
        <v-autocomplete
          class="search-bar"
          :items="this.cities"
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
      </v-col>
      <v-col cols="12" sm="6" md="4">
        <v-autocomplete
          :disabled="this.timestamps.length > 0? false : true"
          class="search-bar"
          :items="this.types"
          prepend-inner-icon="mdi-map-search-outline"
          chips
          deletable-chips
          filled
          rounded
          shadow
          multiple
          v-model="type"
        ></v-autocomplete>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: "Search",
  data: () => ({
    search: null,
    select: null,
    cities: [],
    city: null,
    timestamp: null,
    timestamps: [],
    // todo get types and types mapping
    types: ["construction", "jam"], 
    type: []
  }),
  mounted: function () {
    // get list of all cities
    axios.get('http://localhost:8082/demo/cities/', {
      headers: { 'Access-Control-Allow-Origin': '*' },
  })
  .then(response => {
    this.cities = response.data
  })
  .catch(error => {
      this.errorMessage = error.message
      console.error('There was an error!', error)
      this.loading = false
  })
  },
  methods: {
    // on city selection, get all timestamps for city and emit latest timestamp & city 
    getCity: function () {
      axios.get('http://localhost:8082/demo/timestamps?city='+this.city, {
      headers: { 'Access-Control-Allow-Origin': '*' },
  })
  .then(response => {
    this.timestamps = response.data
    this.timestamp=this.timestamps[this.timestamps.length-1]
    this.$emit('change',{city: this.city, timestamp: this.timestamp})
  })
  .catch(error => {
      this.errorMessage = error.message
      console.error('There was an error!', error)
      this.loading = false
  })
    },
  },
};
</script>

<style>
#search-bar-container {
  padding-top: 100px;
  padding-left: 300px;
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
