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
                    this.timestamps = response.data
                    this.timestamp = this.timestamps[this.timestamps.length - 1]
                    for (let i=0;i<this.type.length;i++) {
                        this.type[i]=this.type[i].replace(/ /g,"_");
                    }
                    this.$emit('change', {
                        city: this.city,
                        timestamp: this.timestamp,
                        type: this.type,
                    })
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
