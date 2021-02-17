<template>
    <div class="about-container">
        <v-row justify="space-around">
            <v-col justify="space-around">
                <v-card class="about-card">
                    <v-card-text>
                        <div>
                            The purpose of this solution is to aggregate traffic
                            data from different provider APIs, normalize it and
                            visualize it on a map. <br />
                            <br />
                            Created as a part of the AMOS Project WS20/21 by
                            Team 1.
                        </div>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
    </div>
</template>

<script>
export default {
    name: 'About',
    components: {},
    data() {
        return {}
    },
}
</script>

<style>
.about-container {
    padding-left: 15px;
    padding-right: 15px;
}

@media only screen and (min-width: 1270px) {
    .about-container {
        padding-left: 295px;
    }
}

.about-card {
    border-radius: 20px !important;
    padding: 10px;
}

.about-card .center {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>
