<template>
  <div>
    <search @change="getSearchValue($event)" />
    <open-street-map :polylines="polylines" />
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
    polylines: []
  }),

  methods: {
    getSearchValue: function (value) {
      this.executeQuery(value);
    },
    executeQuery: function (value) {
      this.polylines=[]
      if (value.city!==null) {
        if(value.type.length == 0) {
        axios
        .get("http://localhost:8082/demo/incidents?city=" + value.city + "&timestamp=" + value.timestamp, {
          headers: { "Access-Control-Allow-Origin": "*" },
        })
        .then((response) => {
          this.cityData = response.data;
          this.passCoordinates(response.data.list);
        })
        .catch((error) => {
          this.errorMessage = error.message;
          console.error("There was an error!", error);
        });
      }
      else {
        axios
        .get("http://localhost:8082/withDatabase/incidentsWithTypes?city=" + value.city + "&types=" + value.type[0], {
          headers: { "Access-Control-Allow-Origin": "*" },
        })
        .then((response) => {
          this.cityData = response.data;
          this.passCoordinates(response.data);
        })
        .catch((error) => {
          this.errorMessage = error.message;
          console.error("There was an error!", error);
        });
      }
      }
    },
    /**
     * Processes the incident data of the selected city.
     *
     * @param cityData Incident data from backend
     * @param coordinatesArray Processed strings into coordinates
     * @param lineArray Array which collects the coordinates of one incident
     * @param latitudinal Latitudinal coordinate of a line edge
     * @param longitudinal Longitidinal coordinate of a line edge
     */
    passCoordinates: function (cityData) {
      for (var i = 0; i < cityData.length; i++) {
        var coordinatesArray = cityData[i].edges.split(',');
        var lineArray = [];
        for( var j = 0; j < coordinatesArray.length; j++) {
          let latitudinal = coordinatesArray[j].split(':')[0];
          let longitudinal = coordinatesArray[j].split(':')[1];
          if (typeof latitudinal !== 'undefined' && typeof longitudinal !== 'undefined') {
          lineArray.push([
            latitudinal,
            longitudinal
          ]);
          }
        }
        this.polylines.push({
            latlngs: lineArray,
            color: "blue",
          });
      }
    },
  },
};
</script>

<style>
</style>
