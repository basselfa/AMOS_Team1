<template>
    <div class="map-wrapper">
        <l-map
            ref="map"
            id="osm-map"
            :zoom="zoom"
            :center="center"
            :options="mapOptions"
        >
            <l-tile-layer :url="url" :attribution="attribution" />
            <l-polyline
                v-for="(polyline, index) in polylines"
                :key="index"
                :lat-lngs="polyline.latlngs"
                :color="polyline.color"
                ><l-tooltip sticky="true">
                    {{ polyline.description }}
                    <br />
                    <span style="font-weight: 500">{{ polyline.type }}</span
                    >,
                    <span style="color: rgb(230, 70, 80); font-weight: 500"
                        >Criticality: {{ polyline.criticality }}</span
                    >, Length: {{ polyline.length }}m
                    <br />
                    <span
                        v-if="
                            polyline.startPositionStreet != null &&
                            polyline.startPositionStreet.length > 3
                        "
                        >{{ polyline.startPositionStreet }},</span
                    >
                    <span v-else>No address provided</span>
                    <span v-if="polyline.city != null">{{
                        polyline.city
                    }}</span>
                    <br />
                    From: {{ polyline.entryTime }} Until: {{ polyline.endTime }}
                    <br />
                    Provider:
                    <span
                        v-if="polyline.provider == '1'"
                        style="font-weight: 700"
                        >TomTom</span
                    ><span
                        v-if="polyline.provider == '0'"
                        style="font-weight: 700"
                        >Here</span
                    >
                </l-tooltip>
            </l-polyline>
            <l-marker :lat-lng="center">
                <l-tooltip>Center</l-tooltip>
            </l-marker>
        </l-map>
        <div class="legend">
            Legend:
            <ul class="legend-items">
                <li><span class="legend-item-tomtom"></span>TomTom</li>
                <li><span class="legend-item-here"></span>Here</li>
                <li><span class="legend-item-both"></span>Both</li>
            </ul>
        </div>
    </div>
</template>

<script>
import { latLng } from 'leaflet'
import {
    LMap,
    LTileLayer,
    LPolyline,
    LMarker,
    LPopup,
    LTooltip,
} from 'vue2-leaflet'
export default {
    name: 'OpenStreetMap',
    props: ['polylines', 'cityCenter'],
    components: {
        LMap,
        LTileLayer,
        LMarker,
        LTooltip,
        LPolyline,
    },
    /**
     * Watch if cityCenter changes
     * and move the map to the new center
     *
     */
    watch: {
        cityCenter: {
            deep: true,
            handler: function (newVal, oldVal) {
                this.center = latLng(
                    this.cityCenter.latitude,
                    this.cityCenter.longitude
                )
                this.$refs.map.setCenter(this.center)
                this.$refs.map.setZoom(10)
            },
        },
    },
    data() {
        return {
            zoom: 10,
            center: latLng(52.51830694705926, 13.325126085286316),
            url: 'https://{s}.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png',
            attribution:
                '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
            showParagraph: false,
            mapOptions: {
                zoomSnap: 0.5,
            },
            showMap: true,
        }
    },
}
</script>

<style>
.map-wrapper {
    height: 100%;
}

#osm-map {
    height: calc(100% - 310px);
    width: calc(100vw - 45px);
    margin: 0;
    position: absolute;
    top: 270px;
    left: 15px;
    border-radius: 20px;
    -webkit-box-shadow: 4px 12px 31px -10px #cecece !important;
    box-shadow: 4px 12px 31px -10px #cecece !important;
    z-index: 0;
}

@media only screen and (min-width: 600px) {
    #osm-map {
        top: 250px;
        height: calc(100% - 300px);
    }
}

@media only screen and (min-width: 960px) {
    #osm-map {
        top: 160px;
        height: calc(100% - 200px);
    }
}

@media only screen and (min-width: 1270px) {
    #osm-map {
        top: 160px;
        width: calc(100vw - 330px);
        height: calc(100% - 200px);
        left: 300px;
    }
}

.legend {
    position: fixed;
    bottom: 10px;
    padding-left: 5px;
    float: left;
    display: flex;
    font-size: 13px;
}

.legend-items {
    list-style: none;
    margin-left: -10px;
}
.legend-items li {
    float: left;
    margin-right: 10px;
}
.legend-items span {
    border: 0px;
    float: left;
    width: 30px;
    height: 12px;
    margin: 4px;
}

.legend-items .legend-item-tomtom {
    background-color: rgb(255, 233, 66);
}
.legend-items .legend-item-here {
    background-color: rgb(27, 143, 209);
}
.legend-items .legend-item-both {
    background-color: rgb(255, 85, 18);
}
</style>
