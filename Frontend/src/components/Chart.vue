<script>
import VueCharts from 'vue-chartjs'
import { Bar, Line } from 'vue-chartjs'

export default {
    extends: Bar,
    data: function() {
        return {
            datacollection: {
                labels: [
                    'Timestamp',
                    'Timestamp',
                    'Timestamp',
                    'Timestamp',
                    'Timestamp',
                    'Timestamp',
                    'Timestamp',
                    'Timestamp',
                    'Timestamp',
                    'Timestamp',
                    'Timestamp',
                    'Timestamp',
                ],
                datasets: [
                    {
                        label: 'Provider1',
                        backgroundColor: 'rgb( 67, 146, 192)',
                        data: [
                            800,
                            900,
                            1000,
                            850,
                            820,
                            920,
                            700,
                            1010,
                            999,
                            820,
                            900,
                            50,
                        ],
                    },
                    {
                        label: 'Provider2',
                        backgroundColor: 'rgb( 244, 186, 94)',
                        data: [
                            100,
                            200,
                            300,
                            400,
                            500,
                            600,
                            700,
                            800,
                            900,
                            1000,
                            1100,
                            1200,
                        ],
                    },
                    {
                        label: 'Comparison',
                        backgroundColor: 'rgb( 242, 99, 66)',
                        data: [
                            10,
                            20,
                            30,
                            40,
                            50,
                            60,
                            70,
                            80,
                            90,
                            100,
                            110,
                            120,
                        ],
                    },
                ],
            },
            options: {
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
                    display: false,
                },
                tooltips: {
                    enabled: true,
                    mode: 'single',
                    callbacks: {
                        label: function(tooltipItems, data) {
                            return tooltipItems.yLabel
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
        this.renderChart(this.datacollection, this.options)
    },
}
</script>
