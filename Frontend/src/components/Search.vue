<template>
    <div id="search-bar-container">
        <v-row class="search-group">
            <v-col cols="12" sm="6" md="4" class="search-col">
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
            <v-col cols="12" sm="6" md="8" class="search-col">
                <v-autocomplete
                    :disabled="this.timestamps.length > 0 ? false : true"
                    class="search-bar"
                    :items="this.types"
                    v-model="type"
                    prepend-inner-icon="mdi-map-search-outline"
                    chips
                    deletable-chips
                    filled
                    rounded
                    shadow
                    multiple
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
        cities: [],
        city: null,
        timestamp: null,
        timestamps: [],
        types: [
            'Construction',
            'Accident',
            'Congestion',
            'Disabled vehicle',
            'Road hazard',
            'Road Works',
            'Planned event',
            'Detour',
            'Misc',
            'Weather',
            'Lane closed',
            'Lane restriction',
        ],
        type: [],
    }),
    mounted: function() {
        // get list of all cities
        axios
            .get('http://' + window.location.hostname + ':8082/withDatabase/cities/', {
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
                .get('http://' + window.location.hostname + ':8082/withDatabase/timestamps?city=' + this.city,
                    {
                        headers: { 'Access-Control-Allow-Origin': '*' },
                    }
                )
                .then(response => {
                    console.log("Timestamps received for " + this.city + ": " + response.data.length)
                    this.timestamps = response.data
                    if (this.timestamps.length < 1) {
                        console.error("No timestamps received for this city")
                    } else {
                        this.timestamp = this.timestamps[this.timestamps.length - 1]
                        this.$emit('change', {
                            city: this.city,
                            timestamp: this.timestamp,
                            type: this.type.map(x => x.toUpperCase().replace(/ /g,"")),
                        })
                    }
                })
                .catch(error => {
                    this.errorMessage = error.message
                    console.error('There was an error!', error)
                })
        },
    },
}
</script>

<style>
.search-group .v-autocomplete.v-select.v-input--is-focused input {
    min-width: 0px !important
}

.search-group .search-col {
 padding:0px;
}

@media only screen and (min-width: 600px) {
  .search-group .search-col {
    padding: 10px;
  }
}
</style>
