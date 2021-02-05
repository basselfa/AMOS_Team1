<template>
<div class="config-container">
<v-row justify="space-around">
<v-col justify="space-around">
    <v-card class="config-card">
        <v-card-text>
            <div>This selection will appear for your map and historization data. <br> Select the cities to be investigated <br> by the AMOS traffic tracker.</div>
              <div v-for="(city,index) in cities" :key="index">
                <div ref="form" x-large class="form-chip center">
                
                    <v-row class="form-row" justify="center" >
                        <v-col cols="12" sm="3" md="3">
                            <v-text-field ref="cityName" v-model="cities[index].cityName" label="City" required :value="city.cityName"/>
                        </v-col>
                        <v-col cols="12" sm="2" md="2">
                            <v-text-field ref="centreLatitude" v-model="cities[index].centreLatitude" :rules="rules" label="Latitudinal value for center" required :value="city.centreLatitude"/>
                        </v-col>
                        <v-col cols="12" sm="2" md="2">
                            <v-text-field ref="centreLongitude" v-model="cities[index].centreLongitude" :rules="rules" label="Longitudinal value for center" required :value="city.centreLongitude"/>
                        </v-col>
                        

                        <v-col cols="12" sm="2" md="2">
                            <v-text-field ref="searchRadiusInMeter" v-model="cities[index].searchRadiusInMeter" label="Radius in meter" required :value="city.searchRadiusInMeter"/>
                        </v-col>

                        <v-row justify="end" style="padding-bottom:20px;">
                            <v-col>
                                <v-btn :loading="loading" class="rm-btn" color="submit" small @click="postRequestCityData(city,city.id)">
                                    Save
                                </v-btn>
                                <v-btn :loading="loading" class="rm-btn" color="error" small @click="removeCity(city.id)">
                                    Delete
                                </v-btn>
                            </v-col>
                        </v-row>
                    </v-row>
                </div>
            </div>
        </v-card-text>
        <v-card-actions class="center">
            <v-btn @click="addRow" text :loading="loading" color="blue accent-4" background="grey">
                Add City
            </v-btn>
        </v-card-actions>
    </v-card>
    </v-col>
    </v-row>
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
            value => (value && (value <= 180 && value >= -180)) || 'Must be between -180 and 180 degrees.',
        ],
    }),
    mounted() {
        this.getRequestCityData();
    },

    methods: {
        getRequestCityData: function () {
            axios
                .get('http://' + window.location.hostname + ':8082/withDatabase/cityinformation', {
                    headers: {
                        'Access-Control-Allow-Origin': '*'
                    },
                })
                .then(response => {
                    this.cities = [];
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
        // TODO Set Timeout // will this be done?
        async postRequestCityData(selectedCity, previouscityId) {
            console.log(selectedCity.cityName)
            this.loading = true
            selectedCity.searchRadiusInMeter = parseInt(selectedCity.searchRadiusInMeter);
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
                // TODO? shouldnt the code below be in the then() block?
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
.config-container {
  padding-left: 15px;
  padding-right: 15px;
}

@media only screen and (min-width: 1270px) {
  .config-container {
    padding-left: 295px;
  }
}

.config-card {
  border-radius: 20px !important;
  padding: 10px;
}

.config-card .form-chip {
    width: 100%;
    margin-top: 8px;
    padding:0px 20px;
    background: rgb(243, 243, 243) !important;
    border-radius:10px;
}

.config-card .center {
    display: flex;
    justify-content: center;
    align-items: center;
}

.config-card .rm-btn {
    margin-top: -30px !important
}

@media only screen and (min-width: 690px) {
  .config-card .rm-btn {
    margin-top: 40px !important;
    margin: 0px 10px 0px 10px;
  }
}

.config-card .v-text-field__details {
  display: inline-block;
  min-width:120px;
  white-space: normal;
  height:30px;
}

.config-card .v-text-field {
    margin-top:25px;
}

</style>
