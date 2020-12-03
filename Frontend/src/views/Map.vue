<template>
  <div>
    <search @change="getSearchValue($event)" />
    <open-street-map :polyline="polyline"></open-street-map>
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
    data: null,
    polyline: {
      latlngs: [],
      color: "green",
    },
    search: null,
  }),
  created() {
    this.executeQuery(this.Search.city);
  },
  methods: {
    getSearchValue: function (value) {
      console.log("jetzt hab ichs!" + value);
      this.executeQuery(value);
    },
    executeQuery: function (city) {
      axios
        .get("http://localhost:8082/demo/incidents?city=" + city, {
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
</style>
