<template>
<div>
    <v-card class="mx-auto config-card" max-width="800">
        <v-card-text>
            <div>This selection will appear for your map and historization data. <br> Select the cities to be investigated <br> by amos traffic tracker.</div>
            <div v-for="(city,index) in cities" :key="index">

                <v-chip x-large class="form-chip center">

                    <v-row class="form-row">
                        <v-col cols="2" md="3">
                            <v-text-field label="City" required :value="city.name">
                            </v-text-field>
                        </v-col>
                        <v-col cols="2" md="3">
                            <v-text-field :rules="rules" label="Latitudinal value for center" required :value="city.centerLatitude"></v-text-field>
                        </v-col>
                        <v-col cols="2" md="3">
                            <v-text-field :rules="rules" label="Longitudinal value for center" required :value="city.centerLongitude"></v-text-field>
                        </v-col>
                        <v-col cols="2" md="3">
                            <v-text-field label="Radius" required :value="city.radius"></v-text-field>
                        </v-col>
                    </v-row>
                </v-chip>
            </div>
        </v-card-text>
        <v-card-actions class="center">
            <v-btn @click="postRequestCityData" text color="blue accent-4" background="grey">
                Submit slection
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
        cities: [{
            name: "Berlin",
            centerLatitude: "51.55",
            centerLongitude: "51.55",
            radius: "50"
        }, {
            name: "Frankfurt",
            centerLatitude: "51.55",
            centerLongitude: "51.55",
            radius: "50"
        }, {
            name: "München",
            centerLatitude: "51.55",
            centerLongitude: "51.55",
            radius: "50"
        }, {
            name: "Hamburg",
            centerLatitude: "51.55",
            centerLongitude: "51.55",
            radius: "50"
        }, {
            name: "Köln",
            centerLatitude: "51.55",
            centerLongitude: "51.55",
            radius: "50"
        }, ],
        rules: [
            value => !!value || 'Required.',
            value => (value && 180 <= value >= -180) || 'Must be between -180 and 180 degrees.',
        ],
    }),
    mounted() {},

    methods: {
        getRequestCityData: function () {
            console.log("request triggered");
            axios
                .get('http://' + window.location.hostname + ':8082/withDatabase/cityinformation', {
                    headers: {
                        'Access-Control-Allow-Origin': '*'
                    },
                })
                .then(this.cities = response)
                .catch(error => {
                    this.errorMessage = error.message
                    console.error('There was an error!', error)
                })
            console.log(this.cities);
        },

        postRequestCityData: function () {
            console.log("request triggered");
            axios
                .post('http://' + window.location.hostname + ':8082/withDatabase/cityinformation', this.cities, {
                    headers: {
                        'Access-Control-Allow-Origin': '*'
                    }
                })
                .then(response => this.cities = response.data)
                .catch(error => {
                    this.errorMessage = error.message
                    console.error('There was an error!', error)
                })
            console.log(this.cities);
        }
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
    width: 1200px;
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
</style>
