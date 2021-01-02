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
    cityData: null,
    polylines: [{
        latlngs: [['52.51784','13.28016'],['52.51771','13.28021'],['52.51765','13.28024'],['52.51729','13.28046']],
        color: "blue",
    }]
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
      console.log("<<<<<<<polyliine")
      console.log(this.polylines);
    },
  },
};
</script>

<style>
</style>
