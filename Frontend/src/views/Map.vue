<template>
    <div>
        <search @change="getSearchValue($event)" />
        <open-street-map :polylines="polylines" />
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
        polylines: [],
    }),

    props: ['mapData'],
    mounted() {
        if (this.mapData) {
            this.executeQuery(this.mapData)
        }
    },

    methods: {
        getSearchValue: function(searchValue) {
            this.executeQuery(searchValue)
        },

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
        async getComparison(value) {
            let request_url =
                'http://' +
                window.location.hostname +
                ':8082/withDatabase/comparison?city=' +
                value.city +
                '&timestamp=' +
                value.timestamp

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
        executeQuery: async function(value) {
            this.polylines = []
            if (value.city !== null && value.timestamp !== null) {
                await this.getIncidents(value)
                await this.getComparison(value)
                this.passCoordinates(this.incidentsData)
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
        passCoordinates: function(incidentsData) {
            for (var i = 0; i < incidentsData.length; i++) {
                var coordinatesArray = incidentsData[i].edges.split(',')
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
                var color
                // tomtom = yellow
                if (incidentsData[i].provider == "0") {color = "yellow"}
                // here = blue
                else if (incidentsData[i].provider == "1") {color = "blue"}
                
                // check if overlapping by checking if the incident is contained in comparisonData
                if ( this.comparisonData.some(code => code.tomTomIncidentId === incidentsData[i].id) || this.comparisonData.some(code => code.hereIncidentId === incidentsData[i].id) ) {
                    color = "red"
                    //if ( this.polylines.some(inc => JSON.stringify(inc.latlngs) === JSON.stringify(lineArray) ) === true ) {console.log("hi")}
}
                this.polylines.push({
                    latlngs: lineArray,
                    color: color,
                    criticality: incidentsData[i].size,
                    description: incidentsData[i].description
                        ? incidentsData[i].description.includes('&')
                            ? incidentsData[i].description.split('&')[1]
                            : incidentsData[i].description
                        : 'Description not available',
                    length: incidentsData[i].lengthInMeter,
                    type: incidentsData[i].type,
                })
                
            }
        },
    },
}
</script>

<style></style>
