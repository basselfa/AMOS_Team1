<template>
  <div style="height: 500px; width: 100%">
    <l-map id="osm-map" :zoom="zoom" :center="center" :options="mapOptions">
      <l-tile-layer :url="url" :attribution="attribution" />
      <l-polyline
        :lat-lngs="polyline.latlngs"
        :color="polyline.color"
      ></l-polyline>
      <l-marker :lat-lng="markerLatLng">
        <!-- <l-icon :icon-url="customMarker"></l-icon> -->
        <l-tooltip>This is an incident</l-tooltip>
      </l-marker>
    </l-map>
  </div>
</template>

<script>
import { latLng } from "leaflet";
import {
  LMap,
  LTileLayer,
  LPolyline,
  LMarker,
  LPopup,
  // LIcon,
  LTooltip,
} from "vue2-leaflet";
export default {
  name: "OpenStreetMap",
  props: ['polyline'],
  components: {
    LMap,
    LTileLayer,
    LMarker,
    // LIcon,
    LTooltip,
    LPolyline,
  },
  data() {
    return {
      zoom: 15,
      center: latLng(52.509041, 13.330550),
      url: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
      attribution:
        '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
      showParagraph: false,
      mapOptions: {
        zoomSnap: 0.5,
      },
      showMap: true,
      markerLatLng: [52.509041, 13.330550],
      // polyline: {
      //   latlngs: this.props.polyline,
      //   color: "green",
      // },
    };
  },
};
</script>

<style>
#osm-map {
  height: 80%;
  width: 60%;
  margin: 0;
  position: absolute;
  top: 680px;
  left: 50%;
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  border-radius: 20px;
      -webkit-box-shadow: 4px 12px 31px -10px #cecece !important;
  box-shadow: 4px 12px 31px -10px #cecece !important;
}
</style>
