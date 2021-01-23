<template>
<div>
    <v-card class="mx-auto config-card" max-width="800">
        <v-card-text>
            <div>Select the cities to be investigated <br> by amos traffic tracker.</div>
            <div v-for="city in cities" v-bind:key="city">
                <v-chip x-large class="form-chip center">
                    <v-row class="form-row">
                        <v-col cols="12" md="4">
                            <v-text-field :rules="rules" :counter="10" label="City" required> {{city.name}}</v-text-field>
                        </v-col>

                        <v-col cols="12" md="4">
                            <v-text-field :rules="rules" :counter="10" label="Center coordinates" required>{{city.center}}</v-text-field>
                        </v-col>

                        <v-col cols="12" md="4">
                            <v-text-field label="Radius" required>{{city.radius}}</v-text-field>
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
            center: "51.55",
            radius: "50"
        }, {
            name: "Frankfurt",
            center: "51.55",
            radius: "50"
        }, {
            name: "München",
            center: "51.55",
            radius: "50"
        }, {
            name: "Hamburg",
            center: "51.55",
            radius: "50"
        }, {
            name: "Köln",
            center: "51.55",
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
    margin-top: max(100px, 5%);
}

.form-chip {
    width: 700px;
    margin: 10px;
    background: rgb(243, 243, 243) !important;
}

.form-row {
    width: 800px;
}

.center {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>
