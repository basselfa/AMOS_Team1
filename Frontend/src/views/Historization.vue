<template>
    <div class="body-ctn" id="historization-table">
        <v-row justify="space-around">
            <v-col justify="space-around">
                <v-card>
                    <div style="padding:10px;">
                        <v-card-title>
                            Traffic Data
                        </v-card-title>

                        <v-row justify="center">
                            <v-col cols="12" sm="4">
                                <v-autocomplete
                                    class="search-bar"
                                    :items="this.cities"
                                    prepend-inner-icon="mdi-map-search-outline"
                                    placeholder="Choose a city"
                                    v-model="city"
                                    @change="fetchDataForCity()"
                                ></v-autocomplete>
                            </v-col>

                            <v-col cols="12" sm="4">
                                <date-time-picker
                                    v-model="startTime"
                                    :label="startLabel"
                                    @change="fetchDataForCity()"
                                />
                            </v-col>
                            <v-col cols="12" sm="4">
                                <date-time-picker
                                    v-model="endTime"
                                    :label="endLabel"
                                    @change="fetchDataForCity()"
                                />
                            </v-col>
                        </v-row>
                    </div>
                </v-card>
            </v-col>
        </v-row>
        <br />
        <v-progress-circular
            v-if="loading == true"
            indeterminate
            color="primary"
            style="margin-top:10%"
        ></v-progress-circular>
        <div
            id="charts-container"
            v-if="loading == false && chartDataCollection !== null"
        >
            <div id="chart-comparison" style="margin-top:20px;">
                {{ this.city }}
                <chart
                    :city="this.city"
                    :chartDataCollection="this.chartDataCollection"
                />
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import Chart from '../components/Chart'
import DateTimePicker from '../components/DateTimePicker'

export default {
    name: 'Historization',
    components: { Chart, DateTimePicker },
    created() {
        this.fetchData()
    },
    data() {
        return {
            errorMessage: null,
            loading: null,
            city: null,
            cities: [],
            startTime: '',
            startLabel: 'Start time',
            endTime: '',
            endLabel: 'End time',
            comparisonData: {
                labels: [],
                tomtom: [],
                here: [],
                comparison: [],
            },
        }
    },
    methods: {
        async fetchData() {
            await axios 
                .get('http://' + window.location.hostname + ':8082/withDatabase/cities', {
                    headers: { 'Access-Control-Allow-Origin': '*' },
                })
                .then(response => {
                    let cities = []
                    response.data.map(function(item) {  
                    cities.push(item.city);
                    })
                    this.cities = cities
                })
                .catch(error => {
                    this.errorMessage = error.message
                    console.error('There was an error!', error)
                    this.loading = false
                })
        },
        async fetchDataForCity() {
            // filters need to be set
            if (this.city && this.startTime && this.endTime) {
                // clear data
                this.comparisonData = {
                    labels: [],
                    tomtom: [],
                    here: [],
                    comparison: [],
                }
                this.loading = true
                // get comparison data   
                await axios
                    .get(
                        'http://' + window.location.hostname + ':8082/demo/comparisonEvaluationOverTime/?city=' +
                            this.city,
                        {
                            headers: { 'Access-Control-Allow-Origin': '*' },
                        }
                    )
                    .then(response => {
                        console.log(response.data)
                        for (let i = 0; i < response.data.length; i++) {
                            // if timestamp between start and end times
                            if (
                                response.data[i].date >= this.startTime &&
                                response.data[i].date <= this.endTime
                            ) {
                                console.log(response)
                                // get the necessary data for the chart
                                this.comparisonData.labels.push(
                                    response.data[i].date
                                )
                                this.comparisonData.tomtom.push(
                                    response.data[i].tomTomIncidentsAmount
                                )
                                this.comparisonData.here.push(
                                    response.data[i].hereIncidentsAmount
                                )
                                this.comparisonData.comparison.push(
                                    response.data[i].sameIncidentAmount
                                )
                            }
                        }
                        this.chartDataCollection = {
                            labels: this.comparisonData.labels,

                            datasets: [
                                {
                                    label: 'Here',
                                    backgroundColor: 'rgb( 67, 146, 192)',
                                    data: this.comparisonData.here,
                                },
                                {
                                    label: 'TomTom',
                                    backgroundColor: 'rgb( 244, 186, 94)',
                                    data: this.comparisonData.tomtom,
                                },
                                {
                                    label: 'Same incidents',
                                    backgroundColor: 'rgb( 242, 99, 66)',
                                    data: this.comparisonData.comparison,
                                },
                            ],
                        }
                        this.loading = false
                    })
                    .catch(error => {
                        this.errorMessage = error.message
                        console.error('There was an error!', error)
                        this.loading = false
                    })
            }
        },
    },
}
</script>

<style>
.body-ctn {
    overflow: hidden;
    padding-left: 295px;
    padding-top: 75px;
}
</style>
