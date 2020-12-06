<template>
  <div class="body-ctn" id="historization-table">
    <v-row>
      <v-col>
        <v-card>
          <v-card-title>
            Traffic Data
            <v-spacer></v-spacer>
            <v-text-field
              v-model="search"
              append-icon="mdi-magnify"
              label="Search"
              single-line
              hide-details
            ></v-text-field>
          </v-card-title>
          <v-progress-circular v-if="loading==true"
            indeterminate
            color="primary"
          ></v-progress-circular>
          <v-data-table v-if="loading==false"
            :headers="headers"
            :items="historizationData"
            :search="search"
            rounded
          ></v-data-table>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Historization",
  components: {

  },
  created () {
    this.fetchData()
  },
   data () {
      return {
        search: '',
        error: null,
        loading: null,
        historizationData: null,
        headers: [
          {
            text: 'Provider',
            align: 'start',
            sortable: false,
            value: 'provider',
          },
          { text: 'Type', value: 'type' },
          { text: 'Size', value: 'size' },
          { text: 'Start position street', value: 'startPositionStreet' },
          { text: 'End position street', value: 'endPositionStreet' },
          { text: 'City', value: 'city' },
        ]
      }
    },
    methods : {
      async fetchData () {
        this.error = null
        this.loading = true
        await axios.get("http://localhost:8082/demo/historization/", {
          headers: { "Access-Control-Allow-Origin": "*" }
        }).then(response => {
          // currently the structure for demo is {incidents: []} and for prod is {[]}
          this.historizationData = response.data.incidents
          this.loading = false
        }).catch((error) => {
          this.errorMessage = error.message;
          console.error("There was an error!", error);
          this.loading = false
      });
    }
  }
};
</script>

<style>
.body-ctn{
  overflow: hidden;
  padding-left: 295px;
  padding-top: 75px;
}
</style>
