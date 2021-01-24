<template>
<div>
    <v-card class="mx-auto config-card" max-width="800" rounded>
        <v-card-text>
            <div>Select the cities to be investigated <br> by amos traffic tracker.</div>
            <div v-for="(city,index) in cities" :key="index" >

                <v-chip x-large class="form-chip center">

                    <v-row class="form-row">

                        <v-col cols="2" md="3">
                            <v-text-field :rules="rules" :counter="10" label="City" required :value="city.name">
                            </v-text-field>
                        </v-col>

                        <v-col cols="2" md="3">
                            <v-text-field :rules="rules"  label="Latitudinal value for center" required :value="city.centerLatitude"></v-text-field>
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
            <v-btn text color="deep-blue accent-4">
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
            value => (value && value.length >= 3) || 'Min 3 characters',
        ],
    }),
    mounted() {},

    methods: {},
}
</script>

<style>
.config-card {
    margin-top: max(150px, 8%);
    border-radius: 30px !important;
}

.form-chip {
    width: 1200px;
    margin: 10px;
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
