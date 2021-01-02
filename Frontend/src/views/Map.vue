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
      color: "blue",
    }
  }),

  methods: {
    getSearchValue: function (value) {
      this.executeQuery(value);
    },
    executeQuery: function (value) {
      axios
        .get("http://localhost:8082/demo/incidents?city=" + value.city + "&timestamp=" + value.timestamp, {
          headers: { "Access-Control-Allow-Origin": "*" },
        })
        .then((response) => {
          console.log(response.data.list);
          this.cityData = response.data;
          this.passCoordinates(response.data.list);
        })
        .catch((error) => {
          this.errorMessage = error.message;
          console.error("There was an error!", error);
        });
    },

    passCoordinates: function (cityData) {
      console.log("current citydata:")
      console.log(cityData)
      for (var i = 0; i < cityData.length; i++) {
        var coordinatesArray = cityData[i].edges.split(',');
        //coordinatesArray //coordinatesArray.split(':').[0]
        for( var j = 0; j < coordinatesArray.length; j++) {
          let latitudinal = coordinatesArray[j].split(':')[0];
          let longitudinal = coordinatesArray[j].split(':')[1];
          if (typeof latitudinal !== 'undefined' && typeof longitudinal !== 'undefined') {
          var lineArray = [];
          this.polyline.latlngs.push([
          latitudinal, //latitudinal value
          longitudinal //longitudinal value
          ]);
          }
        }
        // this.polyline.latlngs.push([
        //   lineArray
        //   ]);
      }
      console.log("<<<<<<<polyliine")
      console.log(this.polyline);
    },
  },
};
</script>

<style>
</style>
