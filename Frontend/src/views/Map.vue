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
        getSearchValue: function(value) {
            this.executeQuery(value)
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
                //console.log(this.incidentsData.length)
                //console.log(this.comparisonData.length)
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
        passCoordinates: function(cityData) {
            for (var i = 0; i < cityData.length; i++) {
                var coordinatesArray = cityData[i].edges.split(',')
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
                this.polylines.push({
                    latlngs: lineArray,
                    color: 'blue',
                    criticality: cityData[i].size,
                    description: cityData[i].description !== 'undefined' ? cityData[i].description.split('&')[1] : 'Description not available',
                    length: cityData[i].lengthInMeter,
                    type: cityData[i].type,
                })
            }
        },
    },
}
</script>

<style></style>
