<template>
    <div class="map-container">
        <search @change="getSearchValue($event)" />
        <open-street-map :polylines="polylines" :cityCenter="cityCenter" />
    </div>
</template>

<script>
import axios from 'axios'
import OpenStreetMap from '../components/OpenStreetMap'
import Search from '../components/Search.vue'
import { latLng } from 'leaflet'
import { LMap, LTileLayer, LMarker, LPopup, LTooltip } from 'vue2-leaflet'

export default {
    name: 'Map',
    components: {
        OpenStreetMap,
        Search,
    },

    data: () => ({
        incidentsData: [],
        comparisonData: [],
        polylines: [],
        cityCenter: ''
    }),

    props: ['mapData'],
    mounted() {
        if (this.mapData) {
            this.executeQuery(this.mapData)
        }
    },

    methods: {
        /**
         * Get the data according to the search criteria and process it
         *
         * @param searchValue Object that contains city, timestamp, and type
         */
        getSearchValue: function(searchValue) {
            this.executeQuery(searchValue)
        },

        /**
         * Get incidents from backend
         *
         * @param value Object that contains city, timestamp, and type
         */
        async getCenter(value) {
            let request_url =
                'http://' +
                window.location.hostname +
                ':8082/withDatabase/cities'
            await axios
                .get(request_url, {
                    headers: { 'Access-Control-Allow-Origin': '*' },
                })
                .then(response => {
                    for (let i = 0; i < response.data.length; i++) {
                        if (response.data[i].city == value.city) {
                            this.cityCenter = response.data[i].centerPoint
                        }
                    }
                })
                .catch(error => {
                    this.errorMessage = error.message
                    console.error('There was an error!', error)
                })
        },
        /**
         * Get incidents from backend
         *
         * @param value Object that contains city, timestamp, and type
         */
        async getIncidents(value) {
            let request_url =
                'http://' +
                window.location.hostname +
                ':8082/withDatabase/incidents?city=' +
                value.city +
                '&timestamp=' +
                value.timestamp
            if (value.type.length !== 0) {
                request_url = request_url + '&types=' + value.type.join()
            }
            if (value.provider !== null) {
                request_url = request_url + '&provider=' + value.provider.join()
            }
            await axios
                .get(request_url, {
                    headers: { 'Access-Control-Allow-Origin': '*' },
                })
                .then(response => {
                    this.incidentsData = response.data
                })
                .catch(error => {
                    this.errorMessage = error.message
                    console.error('There was an error!', error)
                })
        },
        /**
         * Get incident comparison from backend
         *
         * @param value Object that contains city and timestamp
         */
        async getComparison(value) {
            let request_url =
                'http://' +
                window.location.hostname +
                ':8082/withDatabase/comparison?city=' +
                value.city +
                '&timestamp=' +
                value.timestamp
            if (value.provider !== null) {
                request_url = request_url + '&provider=' + value.provider.join()
            }
            await axios
                .get(request_url, {
                    headers: { 'Access-Control-Allow-Origin': '*' },
                })
                .then(response => {
                    this.comparisonData = response.data
                })
                .catch(error => {
                    this.errorMessage = error.message
                    console.error('There was an error!', error)
                })
        },
        /**
         * Get incidents and incident comparison from backend and process the data for the map
         *
         * @param value Object that contains city, timestamp, (and type)
         */
        executeQuery: async function(value) {
            this.polylines = []
            if (value.city !== null && value.timestamp !== null) {
                await this.getCenter(value).then( async () => {
                  await this.getIncidents(value)
                  console.log("Incidents received: " + this.incidentsData.length)
                  await this.getComparison(value)
                  console.log("Comparison incidents received: " + this.comparisonData.length)
                  this.passCoordinates(this.incidentsData)
                })
            }
        },

        /**
         * Processes the incident data of the selected city.
         *
         * @param incidentsData Incidents data from backend
         */
        passCoordinates(incidentsData) {
            for (var i = 0; i < incidentsData.length; i++) {
                // get edges for polyline
                var lineArray = this.getEdges(incidentsData[i])
                // get color for polyline
                var color = this.compareIncidents(incidentsData[i])
                // add polyline to the polylines array
                this.polylines.push({
                    latlngs: lineArray,
                    color: color,
                    criticality: incidentsData[i].size,
                    description: incidentsData[i].description
                        ? incidentsData[i].description.includes('&')
                            ? incidentsData[i].description.split('&')[1]
                            : incidentsData[i].description
                        : 'Description not available',
                    length: incidentsData[i].lengthInMeter.toFixed(2),
                    type: incidentsData[i].type,
                    provider: incidentsData[i].provider,
                    city: incidentsData[i].city,
                    startPositionStreet: incidentsData[i].startPositionStreet,
                    endPositionStreet: incidentsData[i].endPositionStreet,
                    entryTime: incidentsData[i].entryTime,
                    endTime: incidentsData[i].endTime
                })
            }
        },

        /**
         * Get incident edges for the map polylines
         *
         * @param incident An incident object
         */
        getEdges(incident) {
            var coordinatesArray = incident.edges.split(',')
            var lineArray = []
            for (var j = 0; j < coordinatesArray.length; j++) {
                let latitudinal = coordinatesArray[j].split(':')[0]
                let longitudinal = coordinatesArray[j].split(':')[1]
                if (
                    typeof latitudinal !== 'undefined' &&
                    typeof longitudinal !== 'undefined'
                ) {
                    lineArray.push([latitudinal, longitudinal])
                }
            }
            return lineArray
        },

        /**
         * Check if incidents for both providers are overlapping and assign polyline colors
         *
         * @param incident An incident object
         */
        compareIncidents(incident) {
            // check if overlapping by checking if the incident is contained in comparisonData
            let overlapping = false
            if (this.comparisonData.length > 0) {
             overlapping =
                this.comparisonData.some(
                    comparisonIncident =>
                        comparisonIncident.tomTomIncidentId === incident.id
                ) ||
                this.comparisonData.some(
                    comparisonIncident =>
                        comparisonIncident.hereIncidentId === incident.id
                )
            }
            // get incident provider
            let here = incident.provider == '0'
            let tomtom = incident.provider == '1'

            // assign colors
            let color
            // tomtom = yellow
            if (tomtom) {
                color = 'rgb(255, 233, 66)'
            }
            // here = blue
            else if (here) {
                color = 'rgb( 27, 143, 209)'
            }
            // same = red
            if (overlapping) {
                console.log("Overlapping found")
                color = 'rgb(255, 85, 18)'
            }

            return color
        },
    },
}
</script>

<style>
.map-container {
  padding-left: 15px;
  padding-right: 15px;
  overflow: hidden;
}

@media only screen and (min-width: 1270px) {
  .map-container {
    padding-left: 295px;
  }
}

.map-container .v-text-field__details {
    display:none;
    height: 0px;
    margin:0px !important;
}
</style>
