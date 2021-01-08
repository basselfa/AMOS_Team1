<template>
    <div id="search-bar-container">
        <v-row>
            <v-col cols="12" sm="6" md="8">
                <v-autocomplete
                    class="search-bar"
                    :items="this.cities"
                    prepend-inner-icon="mdi-map-search-outline"
                    chips
                    deletable-chips
                    filled
                    rounded
                    shadow
                    solo
                    v-model="city"
                    @change="getCity()"
                ></v-autocomplete>
            </v-col>
            <v-col cols="12" sm="6" md="4">
                <v-autocomplete
                    :disabled="this.timestamps.length > 0 ? false : true"
                    class="search-bar"
                    :items="this.types"
                    prepend-inner-icon="mdi-map-search-outline"
                    chips
                    deletable-chips
                    filled
                    rounded
                    shadow
                    multiple
                    v-model="type"
                    @change="getCity()"
                ></v-autocomplete>
            </v-col>
        </v-row>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'Search',
    data: () => ({
        search: null,
        select: null,
        cities: [],
        city: null,
        timestamp: null,
        timestamps: [],
        // todo get types and types mapping
        types: [
            'Accident',
            'Congestion',
            'Disabled vehicle',
            'Road hazard',
            'Road Works',
            'Planned event',
            'Detour',
            'Misc',
            'Weather',
            'LANECLOSED',
            'Lane restriction',
        ],
        type: [],
    }),
    mounted: function() {
        // get list of all cities
        axios
            .get('http://localhost:8082/demo/cities/', {
                headers: { 'Access-Control-Allow-Origin': '*' },
            })
            .then(response => {
                this.cities = response.data
            })
            .catch(error => {
                this.errorMessage = error.message
                console.error('There was an error!', error)
                this.loading = false
            })
    },
    methods: {
        /**
         * Triggers REST Request to backend for..
         *
         * @param city .. a selected city.
         * @param timestamp .. the latest time stamp.
         */
        getCity: function() {
            axios
                .get(
                    'http://localhost:8082/demo/timestamps?city=' + this.city,
                    {
                        headers: { 'Access-Control-Allow-Origin': '*' },
                    }
                )
                .then(response => {
                    this.timestamps = response.data
                    this.timestamp = this.timestamps[this.timestamps.length - 1]
                    this.$emit('change', {
                        city: this.city,
                        timestamp: this.timestamp,
                        type: this.type,
                    })
                })
                .catch(error => {
                    this.errorMessage = error.message
                    console.error('There was an error!', error)
                    this.loading = false
                })
        },
    },
}
</script>

<style>
#search-bar-container {
    padding-top: 100px;
    padding-left: 300px;
    padding-right: 20%;
}

#search-icon {
    float: left;
    padding-top: 6px;
    padding-right: 6px;
}
</style>
