<template>
  <div class="historization-container" id="historization-table">
    <v-row justify="space-around">
      <v-col justify="space-around">
        <v-card class="historization-card">
          <div>
            <v-card-title> Traffic Data </v-card-title>

            <v-row justify="center" style="padding: 0px 20px">
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
                <date-time-picker
                  v-model="startTime"
                  :label="startLabel"
                  @change="fetchDataForCity()"
                />
              </v-col>
              <v-col cols="12" sm="4">
                <date-time-picker
                  v-model="endTime"
                  :label="endLabel"
                  @change="fetchDataForCity()"
                />
              </v-col>
            </v-row>
            <v-row>
              <small style="padding: 0px 30px; margin-top:-10px">
                The overlapping incident totals (red) are a subset of the
                respective number of total incidents.
              </small>
            </v-row>
            <br />
            <v-progress-circular
              v-if="loading == true"
              indeterminate
              color="primary"
              class="historization-loading-indicator"
            ></v-progress-circular>
            <div
              id="charts-container"
              v-if="loading == false && chartDataCollection !== null"
            >
              <div id="chart-comparison">
                {{ this.city }}
                <chart
                  :city="this.city"
                  :chartDataCollection="this.chartDataCollection"
                  :legend=true
                />
              </div>
            </div>
            <div
              id="charts-container"
              v-if="loading == false && chartDataCollection == null"
            >
              <div id="chart-comparison-default">
                <chart
                  :city="this.city"
                  :chartDataCollection="this.chartDataDefault"
                  :legend=false
                />
              </div>
            </div>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import axios from "axios";
import Chart from "../components/Chart";
import DateTimePicker from "../components/DateTimePicker";

export default {
  name: "Historization",
  components: { Chart, DateTimePicker },
  created() {
    this.fetchData();
  },
  data() {
    return {
      errorMessage: null,
      loading: false,
      city: null,
      cities: [],
      startTime: "",
      startLabel: "Start time",
      endTime: "",
      endLabel: "End time",
      comparisonData: {
        labels: [],
        tomtom: [],
        here: [],
        comparison: [],
      },
      chartDataCollection: null,
      chartDataDefault: {
              labels: ['', '', ''],
              datasets: [
                {
                  label: "",
                  backgroundColor: "rgb( 67, 146, 192)",
                  data: [0,0,0],
                },
                {
                  label: "",
                  backgroundColor: "rgb( 244, 186, 94)",
                  data: [0,0,0],
                },
                {
                  label: "",
                  backgroundColor: "rgb( 242, 99, 66)",
                  data: [0,0,0],
                }
              ]
            }
    }
  },
  methods: {
    /**
    * Get all cities with request
    * and save them in this.cities
    */
    async fetchData() {
      await axios
        .get(
          "http://" + window.location.hostname + ":8082/withDatabase/cityinformation",
          {
            headers: { "Access-Control-Allow-Origin": "*" },
          }
        )
        .then((response) => {
          let cities = [];
          response.data.map(function (item) {
            cities.push(item.cityName);
          });
          this.cities = cities;
        })
        .catch((error) => {
          this.errorMessage = error.message;
          console.error("There was an error!", error);
          this.loading = false;
        });
    },
    /**
    * Get the comparison evaluation data for the selected city if start and end times are also selected
    *
    */
    async fetchDataForCity() {
      // filters need to be set
      if (this.city && this.startTime && this.endTime) {
        // clear data
        this.comparisonData = {
          labels: [],
          tomtom: [],
          here: [],
          comparison: [],
        };
        this.loading = true;
        // get comparison evaluation data
        await axios
          .get(
            "http://" +
              window.location.hostname +
              ":8082/withDatabase/comparisonEvaluationOverTime/?city=" +
              this.city,
            {
              headers: { "Access-Control-Allow-Origin": "*" },
            }
          )
          .then((response) => {
            console.log("Historization data received:" + response.data.length);
            for (let i = 0; i < response.data.length; i++) {
              // if timestamp between start and end times
              if (
                response.data[i].date >= this.startTime &&
                response.data[i].date <= this.endTime
              ) {
                console.log(response.data[i].date);
                // get the necessary data for the chart
                this.comparisonData.labels.push(response.data[i].date);
                this.comparisonData.tomtom.push(
                  response.data[i].tomTomIncidentsAmount
                );
                this.comparisonData.here.push(
                  response.data[i].hereIncidentsAmount
                );
                this.comparisonData.comparison.push(
                  response.data[i].sameIncidentAmount
                );
              }
            }
            // datasets for the chart visialization
            this.chartDataCollection = {
              labels: this.comparisonData.labels,

              datasets: [
                {
                  label: "Here",
                  backgroundColor: "rgb( 67, 146, 192)",
                  data: this.comparisonData.here,
                },
                {
                  label: "TomTom",
                  backgroundColor: "rgb( 244, 186, 94)",
                  data: this.comparisonData.tomtom,
                },
                {
                  label: "Same incidents",
                  backgroundColor: "rgb( 242, 99, 66)",
                  data: this.comparisonData.comparison,
                },
              ],
            }
            this.loading = false;
          })
          .catch((error) => {
            this.errorMessage = error.message;
            console.error("There was an error!", error);
            this.loading = false;
          });
      }
    },
  },
};
</script>

<style>
.historization-container {
  padding-left: 15px;
  padding-right: 15px;
  overflow: hidden;
}

@media only screen and (min-width: 1270px) {
  .historization-container {
    padding-left: 295px;
  }
}

.historization-card {
  padding: 10px; 
  border-radius:20px !important;
}

.historization-loading-indicator {
  margin-top: 15vw; 
  position:relative; 
  bottom:10vw;
}
</style>
