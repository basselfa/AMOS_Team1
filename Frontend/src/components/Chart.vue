<script>
import VueCharts from 'vue-chartjs'
import { Bar, Line } from 'vue-chartjs'
import router from '../router'

export default {
    extends: Bar,
    props: ['city', 'chartDataCollection'],
    data: function() {
        // needed so we can access the city value inside the onClick function
        const cityName = this.city;
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
                onClick: function(evt, item) {
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
                    display: true,
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
    mounted() {
        if (this.chartDataCollection != null) {
            this.renderChart(this.chartDataCollection, this.options)
        }
    },
}
</script>
