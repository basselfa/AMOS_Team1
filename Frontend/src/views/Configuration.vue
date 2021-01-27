<template>
<div>
    <v-card class="mx-auto config-card" max-width="900">
        <v-card-text>
            <div>This selection will appear for your map and historization data. <br> Select the cities to be investigated <br> by amos traffic tracker.</div>
            <div v-for="(city,index) in cities" :key="index">
                <v-chip ref="form" x-large class="form-chip center">
                    <v-row class="form-row">
                        <v-col cols="4" md="3">
                            <v-text-field ref="cityName" v-model="cities[index].cityName" label="City" required :value="city.cityName">
                            </v-text-field>
                        </v-col>
                        <v-col cols="3" md="2">
                            <v-text-field ref="centreLatitude" v-model="cities[index].centreLatitude" :rules="rules" label="Latitudinal value for center" required :value="city.centreLatitude"></v-text-field>
                        </v-col>
                        <v-col cols="3" md="2">
                            <v-text-field ref="centreLongitude" v-model="cities[index].centreLongitude" :rules="rules" label="Longitudinal value for center" required :value="city.centreLongitude"></v-text-field>
                        </v-col>
                        <v-col cols="3" md="2">
                            <v-text-field ref="searchRadiusInMeter" v-model="cities[index].searchRadiusInMeter" label="Radius" required :value="city.searchRadiusInMeter"></v-text-field>
                        </v-col>
                        <v-col cols="1" md="1">
                            <v-btn :loading="loading" class="rm-btn" color="error" small @click="removeCity(city.id)">
                                Delete
                            </v-btn>
                        </v-col>
                        <v-col cols="1" md="2">
                            <v-btn :loading="loading" class="rm-btn" color="submit" small @click="postRequestCityData(city,city.id)">
                                Save
                            </v-btn>
                        </v-col>
                    </v-row>
                </v-chip>
            </div>
        </v-card-text>
        <v-card-actions class="center">
            <v-btn @click="addRow" text :loading="loading" color="blue accent-4" background="grey">
                Add City
            </v-btn>
        </v-card-actions>
    </v-card>
</div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'Configuration',
    components: {},

    data: () => ({
        cities: [],
        loading: false,
        rules: [
            value => !!value || 'Required.',
            value => (value && 180 <= value >= -180) || 'Must be between -180 and 180 degrees.',
        ],
    }),
    mounted() {
        this.getRequestCityData();
    },

    methods: {
        getRequestCityData: function () {
            console.log("request triggered");
            axios
                .get('http://' + window.location.hostname + ':8082/withDatabase/cityinformation', {
                    headers: {
                        'Access-Control-Allow-Origin': '*'
                    },
                })
                .then(response => {
                    this.cities = [];
                    console.log("from get:");
                    console.log(response);
                    response.data.forEach(city => {
                        this.cities.push(city)
                    });
                })
                .catch(error => {
                    this.errorMessage = error.message
                    console.error('There was an error!', error)
                })
        },

        async removeCity(id) {
            this.loading = true
            const del = await axios.delete('http://' + window.location.hostname + ':8082/withDatabase/cityinformation?id=' + id, {
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                        'accept': 'application/json'
                    },
                })
                .then(console.log("item with id" + id + " was deleted"))
                .catch(error => {
                    this.errorMessage = error.message
                    console.error('There was an error!', error)
                })
            this.loading = false;
            this.getRequestCityData();
        },
        // TODO Set Timeout
        async postRequestCityData(selectedCity, previouscityId) {
            console.log(selectedCity.cityName)
            this.loading = true
            const post = await axios
                .post('http://' + window.location.hostname + ':8082/withDatabase/cityinformation', selectedCity, {
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                        'Content-Type': 'application/json',
                        'accept': 'application/json'
                    },
                })
                .then()
                .catch(error => {
                    this.errorMessage = error.message
                    console.error('There was an error!', error)
                })
            this.removeCity(previouscityId);
            this.getRequestCityData();
            this.loading = false
        },
        addRow: function () {
            var emptyRow = {
                cityName: "",
                centreLatitude: "",
                centreLongitude: "",
                searchRadiusInMeter: 0,
            }
            this.cities.push(emptyRow);
        },
    },
}
</script>

<style>
.config-card {
    margin-top: max(150px, 8%);
    margin-left: max(150px, 8%);
    border-radius: 30px !important;
}

.form-chip {
    width: 1000px;
    margin: 8px;
    background: rgb(243, 243, 243) !important;
}

.form-row {
    /* width: 800px; */
}

.center {
    display: flex;
    justify-content: center;
    align-items: center;
}

.rm-btn {
    margin-top: 18px !important
}
</style>
