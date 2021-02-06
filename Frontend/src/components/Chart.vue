<script>
import VueCharts from 'vue-chartjs'
import { Bar, Line } from 'vue-chartjs'
import router from '../router'

export default {
    extends: Bar,
    /**
    * @prop city - city name
    * @prop chartDataCollection - the dataset for the chart ith the data from Historization
    * @prop legend - if legend should be shown for the chart
    */
    props: ['city', 'chartDataCollection', 'legend'],
    data: function() {
        // we need the city name so we can access the city value inside the onClick function
        const cityName = this.city
        // if legend should be shown for the chart
        const ifLegend = this.legend
        return { 
            // chart options
            options: {
                events: [
                    'mousemove',
                    'mouseout',
                    'click',
                    'touchstart',
                    'touchmove',
                ],
                // on bar click redirect to map view for that city and timestamp
                onClick: function(evt, item) {
                    console.log(cityName)
                    console.log(item[0]['_model'].label)
                    router.push({
                        name: 'Map',
                        params: {
                            mapData: {
                                city: cityName,
                                timestamp: item[0]['_model'].label,
                                type: [],
                            },
                        },
                    })
                },
                scales: {
                    yAxes: [
                        {
                            ticks: {
                                beginAtZero: true,
                            },
                            gridLines: {
                                display: true,
                            },
                        },
                    ],
                    xAxes: [
                        {
                            ticks: {
                                beginAtZero: true,
                            },
                            gridLines: {
                                display: false,
                            },
                        },
                    ],
                },
                legend: {
                    display: ifLegend,
                },
                tooltips: {
                    enabled: true,
                    mode: 'single',
                    callbacks: {
                        label: function(tooltipItems, data) {
                            return (
                                tooltipItems.yLabel +
                                '; click to see on the map'
                            )
                        },
                    },
                },
                responsive: true,
                maintainAspectRatio: false,
                height: 200,
            },
        }
    },
    // if nothing is selected yet, show empty chart
    mounted() {
        if (this.chartDataCollection != null) {
            this.renderChart(this.chartDataCollection, this.options)
        }
    },
}
</script>
