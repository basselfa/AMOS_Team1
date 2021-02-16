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
                    placeholder="Select a city"
                    v-model="city"
                    @change="
                        getCity()
                        setCityChange()
                    "
                ></v-autocomplete>
            </v-col>
            <v-col cols="12" sm="6" md="4" class="search-col">
                <v-autocomplete
                    :disabled="this.timestamps.length > 0 ? false : true"
                    class="search-bar"
                    :items="this.types"
                    v-model="type"
                    prepend-inner-icon="mdi-car-info"
                    chips
                    deletable-chips
                    filled
                    rounded
                    shadow
                    multiple
                    placeholder="Select incident types"
                    @change="getCity()"
                ></v-autocomplete>
            </v-col>
            <v-col cols="12" sm="6" md="3" class="search-col search-provider">
                <v-autocomplete
                    :disabled="this.timestamps.length > 0 ? false : true"
                    class="search-bar"
                    :items="this.providers"
                    v-model="provider"
                    prepend-inner-icon="mdi-head-outline"
                    chips
                    deletable-chips
                    filled
                    rounded
                    shadow
                    placeholder="Select a traffic data provider"
                    @change="getCity()"
                ></v-autocomplete>
            </v-col>
            <v-col
                cols="12"
                sm="1"
                md="1"
                lg="1"
                class="search-col search-refresh"
            >
                <v-btn
                    :disabled="refreshDisabled"
                    class="search-bar btn-refresh"
                    @click="refreshData()"
                >
                    <v-icon
                        v-if="!refreshDisabled && !error"
                        color="blue lighten-1"
                        >mdi-cloud-refresh</v-icon
                    >
                    <v-icon
                        v-if="!refreshDisabled && error"
                        color="red lighten-1"
                        >mdi-cloud-refresh</v-icon
                    >
                    <div
                        v-if="refreshDisabled && !error"
                        class="loadingSpinner"
                    ></div>
                </v-btn>
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
            'Accident',
            'Congestion',
            'Detour',
            'Disabled vehicle',
            'Lane closed',
            'Lane restriction',
            'Misc',
            'Planned event',
            'Road hazard',
            'Road Works',
            'Weather',
        ],
        type: [],
        providers: ['Here', 'TomTom'],
        provider: null,
        cityChange: false,
        refreshDisabled: false,
        errorMessage: null,
    }),
    mounted: function () {
        // get list of all cities
        axios
            .get(
                'http://' +
                    window.location.hostname +
                    ':8082/withDatabase/cityinformation/',
                {
                    headers: { 'Access-Control-Allow-Origin': '*' },
                }
            )
            .then((response) => {
                let cities = []
                response.data.map(function (item) {
                    cities.push(item.cityName)
                })
                this.cities = cities
            })
            .catch((error) => {
                this.error = error.message
                console.error('There was an error!', error)
            })
    },
    methods: {
        /**
        * Emits the event of city change to the parent component,
          so that we know when to call the city center function
        */
        setCityChange: function () {
            this.$emit('city-change')
        },
        /**
         * Triggers REST Request to backend for..
         *
         * @param city .. a selected city.
         * @param timestamp .. the latest time stamp.
         */
        getCity: function () {
            axios
                .get(
                    'http://' +
                        window.location.hostname +
                        ':8082/withDatabase/timestamps?city=' +
                        this.city,
                    {
                        headers: { 'Access-Control-Allow-Origin': '*' },
                    }
                )
                .then((response) => {
                    console.log(
                        'Timestamps received for ' +
                            this.city +
                            ': ' +
                            response.data.length
                    )
                    this.timestamps = response.data
                    if (this.timestamps.length < 1) {
                        console.error('No timestamps received for this city')
                    } else {
                        this.timestamp = this.timestamps[
                            this.timestamps.length - 1
                        ]
                        this.$emit('change', {
                            city: this.city,
                            timestamp: this.timestamp,
                            type: this.type.map((x) =>
                                x.toUpperCase().replace(/ /g, '')
                            ),
                            provider: this.provider,
                        })
                    }
                })
                .catch((error) => {
                    this.errorMessage = error.message
                    console.error('There was an error!', error)
                })
        },
        async refreshData() {
            this.error = null
            this.refreshDisabled = true
            await axios
                .get(
                    'http://' +
                        window.location.hostname +
                        ':8082/withDatabase/refresh/',
                    {
                        headers: { 'Access-Control-Allow-Origin': '*' },
                    }
                )
                .then((response) => {
                    if (response.status == 200) {
                        if (this.city != null) {
                            this.getCity()
                        }
                        this.refreshDisabled = false
                    }
                })
                .catch((error) => {
                    this.refreshDisabled = false
                    this.errorMessage = error.message
                    console.error('There was an error!', error)
                })
        },
    },
}
</script>

<style>
#search-bar-container
    .search-group
    .v-autocomplete.v-select.v-input--is-focused
    input {
    min-width: 0px !important;
}

#search-bar-container .search-group .search-col {
    padding: 0px;
}

@media only screen and (min-width: 600px) {
    #search-bar-container .search-group .search-col {
        padding: 10px;
    }

    #search-bar-container .search-group .col-12 {
        flex: 0 0 100% !important;
    }
}

#search-bar-container .search-group .btn-refresh {
    border-radius: 50px !important;
    padding: 28px 0px !important;
}

#search-bar-container .search-group .search-provider.col-12 {
    flex: 0 0 80% !important;
}

#search-bar-container .search-group .search-refresh.col-12 {
    flex: 0 0 20% !important;
}

@keyframes spin {
    0% {
        transform: rotate(0deg);
    }
    50% {
        transform: rotate(360deg);
    }
    100% {
        transform: rotate(720deg);
    }
}

#search-bar-container .loadingSpinner {
    border: 4px solid transparent;
    border-top: 4px solid rgb(27, 143, 209);
    border-radius: 50%;
    width: 30px;
    height: 30px;
    animation-name: spin;
    animation-duration: 2s;
    animation-timing-function: ease;
    animation-iteration-count: infinite;
}
</style>
