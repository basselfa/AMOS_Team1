<template>
    <div>
        <v-menu
            ref="menu"
            v-model="open"
            :close-on-content-click="false"
            transition="scale-transition"
            offset-y
            class="datetime-modal"
          :max-width="isMobile ? '380px' : '560px'" :min-width="isMobile ? '380px' : '560px'"
        >
        
            <template v-slot:activator="{ on }">
                <v-text-field
                    v-model="displayDate"
                    :label="label"
                    prepend-inner-icon="mdi-calendar-outline"
                    readonly
                    v-on="on"
                ></v-text-field>
            </template>

            <div class="date-time-container">
                <v-layout row wrap>
                    <v-flex xs6 sm6>
                        <v-date-picker
                            v-model="dateModel"
                            color="#3486b5"
                        ></v-date-picker>
                    </v-flex>
                    <v-flex xs6 sm6>
                        <v-time-picker
                            color="#3486b5"
                            v-if="open"
                            v-model="timeModel"
                            :allowed-minutes="v => v == '00'"
                            format="24hr"
                        ></v-time-picker>
                    </v-flex>

                    <v-flex xs12 class="text-xs-center">
                        <h3 class="text-xs-center">{{ currentSelection }}</h3>
                        <v-btn primary small @click="open = false"
                            >Cancel</v-btn
                        >
                        <v-btn small @click="confirm()">Ok</v-btn>
                    </v-flex>
                </v-layout>
            </div>
        </v-menu>
    </div>
</template>

<script>
export default {
    name: 'DateTimePicker',
    props: ['label', 'value'],
    data() {
        return {
            open: false,
            displayDate: '',
            dateModel: '',
            timeModel: '13:00',
            isMobile: false
        }
    },

    computed: {
        /**
         * Display datetime selected in modal
         *
         */
        currentSelection() {
            let selectedTime = this.timeModel
            if (!this.dateModel) return ''
            const [year, month, day] = this.dateModel.split('-')
            let monthNames = ['Jan', 'Feb', 'Mar','Apr', 'May', 'June', 'Jul', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec']
            let monthName = monthNames[parseInt(month)-1]
            return `${monthName} ${day}, ${year}` + ' ' + selectedTime
        },
    },
    /**
    * Watch for screen resize 
    *
    */
    mounted () {
      this.onResize()
      window.addEventListener('resize', this.onResize, { passive: true })
    },

    methods: {
        /**
         * Confirm selected datetime on click
         *
         */
        confirm() {
            this.onUpdateDate()
            this.open = false
            this.$emit('change');
        },
        /**
         * On datetime update, if both date and time are set, set datetime for display 
         */
        onUpdateDate() {
            if (!this.dateModel || !this.timeModel) return false
            this.displayDate = this.dateModel + ' ' + this.timeModel
            this.$emit('input', this.displayDate)
        },
        /**
         * On screen resize set if mobile view 
         * (this is used for conditional rendering of the datetime modal according to the screen size)
         */
        onResize () {
            this.isMobile = window.innerWidth < 540
      },
    },
}
</script>

<style>
.date-time-container {
    background: white;
    padding: 0px 5px 10px 5px;
}

.date-time-container .v-time-picker-title {
    height: 56px !important;
}

.date-time-container .v-picker__title {
    border-radius: 0px !important;
}

.date-time-container .v-card {
    box-shadow: none;
}

.date-time-container .v-time-picker-clock {
    top:20px;
    margin-left: -10px;
    width: 90%;
    flex: 0.9 0 auto !important;
    padding-top: 0 !important;
}
   
@media only screen and (min-width: 540px) {
    .date-time-container .v-time-picker-clock {
        margin-left: 10px;
        width: 90%;
        flex: 0.9 0 auto !important;
        padding-top: 0 !important;
    }
}

.date-time-container .v-picker--date .v-picker__body {
    width:200px !important;
    height:280px !important;
}

.date-time-container .v-picker--time .v-picker__body {
    width:220px !important;
    height:220px !important;
}

@media only screen and (min-width: 540px) {
  .date-time-container .v-picker--time .v-picker__body {
    width:290px !important;  
    height:290px !important;
}
.date-time-container .v-picker--date .v-picker__body {
    width:300px !important;
}

}
</style>
