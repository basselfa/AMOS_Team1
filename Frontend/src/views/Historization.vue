<template>
    <div class="body-ctn" id="historization-table">
        <v-row justify="space-around">
            <v-col justify="space-around">
                <v-card>
                    <div style="padding:10px;">
                    <v-card-title >
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
                            <date-time-picker v-model="startTime" :label='startLabel'/>
                        </v-col>
                        <v-col cols="12" sm="4">
                            <date-time-picker v-model="endTime" :label='endLabel'/>
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
            v-if="loading == false && historizationData !== null"
        >
            <div id="chart-comparison" style="margin-top:50px;">
                <chart :city="this.city" />
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
            historizationData: null,
            errorMessage: null,
            loading: null,
            city: null,
            cities: [],
            startTime: '',
            startLabel: 'Start time',
            endTime: '',
            endLabel: 'End time'
        }
    },
    methods: {
        async fetchData() {
            this.cities = ['Berlin', 'Hamburg'] // todo request

            /*await axios
                .get('http://localhost:8082/demo/historization/', {
                    headers: { 'Access-Control-Allow-Origin': '*' },
                })
                .then(response => {
                    // currently the structure for demo is {incidents: []} and for prod is {[]}
                    this.historizationData = response.data
                    this.loading = false
                })
                .catch(error => {
                    this.errorMessage = error.message
                    console.error('There was an error!', error)
                    this.loading = false
                })*/
        },
        fetchDataForCity() {
            this.loading = true
            this.historizationData = ['placeholder data from request']
            this.loading = false
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
