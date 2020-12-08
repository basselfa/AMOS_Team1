<template>
  <div>
    <search @change="getSearchValue($event)" />
    <open-street-map :polyline="polyline" />
  </div>
</template>

<script>
import axios from "axios";
import OpenStreetMap from "../components/OpenStreetMap";
import Search from "../components/Search.vue";
import { latLng } from "leaflet";
import { LMap, LTileLayer, LMarker, LPopup, LTooltip } from "vue2-leaflet";

export default {
  name: "Map",
  components: {
    OpenStreetMap,
    Search,
  },

  data: () => ({
    cityData: null,
    polyline: {
      latlngs: [],
      color: "green",
    }
  }),

  methods: {
    getSearchValue: function (value) {
      //console.log("jetzt hab ichs!" + value);
      this.executeQuery(value);
    },
    executeQuery: function (city) {
      axios
        .get("http://localhost:8082/demo/incidents?city=" + city, {
          headers: { "Access-Control-Allow-Origin": "*" },
        })
        .then((response) => {
          //console.log(response.data);
          this.cityData = response.data;
          this.passCoordinates(response.data);
        })
        .catch((error) => {
          this.errorMessage = error.message;
          console.error("There was an error!", error);
        });
    },

    passCoordinates: function (cityData) {
      for (var i = 0; i < cityData.incidents[0].shape.length; i++) {
        this.polyline.latlngs.push([
          cityData.incidents[0].shape[i].latitude,
          cityData.incidents[0].shape[i].longitude,
        ]);
        //console.log(this.polyline.latlngs);
      }
    },
  },
};
</script>

<style>
</style>
