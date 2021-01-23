<template>
  <div>
    <l-map id="osm-map" :zoom="zoom" :center="center" :options="mapOptions">
      <l-tile-layer :url="url" :attribution="attribution" />
      <l-polyline
        v-for="(polyline,index) in polylines" :key="index"
        :lat-lngs="polyline.latlngs"
        :color="polyline.color"
      ><l-tooltip sticky="true">{{polyline.description}} <br> <span style="font-weight:500">{{polyline.type}}</span>, <span style="color:rgb( 230, 70, 80);font-weight:500">Criticality: {{polyline.criticality}}</span> Length: {{polyline.length}}</l-tooltip></l-polyline>
      <l-marker :lat-lng="markerLatLng">
        <l-tooltip>Center</l-tooltip>
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
  LTooltip,
} from "vue2-leaflet";
export default {
  name: "OpenStreetMap",
  props: ['polylines'],
  components: {
    LMap,
    LTileLayer,
    LMarker,
    LTooltip,
    LPolyline,
  },
  data() {
    return {
      zoom: 13,
      center: latLng(52.515000, 13.3800575),
      url: "https://{s}.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png",
      attribution:'&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
      showParagraph: false,
      mapOptions: {
        zoomSnap: 0.5,
      },
      showMap: true,
      markerLatLng: [52.509041, 13.330550],
    };
  },
};
</script>

<style>
#osm-map {
  height: 75%;
  width: 70%;
  margin: 0;
  position: absolute;
  top: 200px;
  left: 300px;
  border-radius: 20px;
  -webkit-box-shadow: 4px 12px 31px -10px #cecece !important;
  box-shadow: 4px 12px 31px -10px #cecece !important;
  z-index: 0;
}
</style>
