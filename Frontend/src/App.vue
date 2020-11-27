<template>
  <div id="app">
    <v-app id="main">
      <v-row>
        <v-col>
          <navigation />
          <search />
          <open-street-map :polyline="polyline"></open-street-map>
        </v-col>
      </v-row>
    </v-app>
  </div>
</template>

<script>
import axios from "axios";
import { latLng } from "leaflet";
import { LMap, LTileLayer, LMarker, LPopup, LTooltip } from "vue2-leaflet";
import OpenStreetMap from "./components/OpenStreetMap.vue";
import Navigation from "./components/Navigation.vue";
import Search from "./components/Search.vue";

export default {
  name: "App",
  components: {
    OpenStreetMap,
    Navigation,
    Search,
  },
  data: () => ({
    data: null,
    polyline: {
      latlngs: [],
      color: "green",
    },
  }),
  created() {
    axios
      .get("http://localhost:8082/demo/incidents?city=berlin", {
        headers: { "Access-Control-Allow-Origin": "*" },
      })
      .then((response) => {
        this.data = response.data;
        console.log(this.data);
        console.log(response.data);
        this.passCoordinates(response.data);
      })
      .catch((error) => {
        this.errorMessage = error.message;
        console.error("There was an error!", error);
      });
  },
  methods: {
    passCoordinates: function (data) {
      for (var i = 0; i < data.incidents[0].shape.length; i++) {
        this.polyline.latlngs.push([
          data.incidents[0].shape[i].latitude,
          data.incidents[0].shape[i].longitude,
        ]);
        console.log(this.polyline.latlngs);
      }
    },
  },
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
