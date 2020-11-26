<template>
  <div id="app">
    <v-app id="main">
      <navigation />
      <open-street-map :polyline="polyline"></open-street-map>
    </v-app>
  </div>
</template>

<script>
import axios from "axios";
import { latLng } from "leaflet";
import { LMap, LTileLayer, LMarker, LPopup, LTooltip } from "vue2-leaflet";
import OpenStreetMap from "./components/OpenStreetMap.vue";
import Navigation from "./components/Navigation.vue";

export default {
  name: "App",
  components: {
    OpenStreetMap,
    Navigation,
  },
  data: () => ({
    data: null,
    polyline: {
        latlngs: [
          // [47.334852, -1.509485],
          // [47.342596, -1.328731],
          // [47.241487, -1.190568],
          // [47.234787, -1.358337],
        ],
        color: "green",
      },
  }),
  created() {
    // GET request using axios with error handling
    // Backend example resuest url: "http://localhost:8082/search?city=berlin"
    // TOM TOM example request url: https://api.tomtom.com/traffic/services/4/incidentDetails/s3/6841263.950712%2C511972.674418%2C6886056.049288%2C582676.925582/10/-1/json?geometries=original&key=roWIhh9zqoIwMRfhGTc2UvQIshzr2fte
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
    passCoordinates: function(data) {
      for(var i = 0; i< data.incidents[0].shape.length; i++){
        this.polyline.latlngs.push([data.incidents[0].shape[i].latitude,data.incidents[0].shape[i].longitude]);
        console.log(this.polyline.latlngs)
      }
    }
  }
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
