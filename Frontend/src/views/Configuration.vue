<template>
<div>
    <v-card class="mx-auto config-card" max-width="800">
        <v-card-text>
            <div>Select the cities to be investigated <br> by amos traffic tracker.</div>
            <div class="text--primary">
                <v-text-field label="Main input" :rules="rules" hide-details="auto"></v-text-field>
                <v-text-field label="Another input"></v-text-field>
            </div>
        </v-card-text>
        <v-card-actions>
            <v-btn text color="deep-blue accent-4">
                Submit slection
            </v-btn>
        </v-card-actions>
    </v-card>
</div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'Configuration',
    components: {},

    data: () => ({
        cities: [],
        rules: [
            value => !!value || 'Required.',
            value => (value && value.length >= 3) || 'Min 3 characters',
        ],
    }),
    mounted() {},

    methods: {},
}
</script>

<style>
.config-card {
    margin-top: max(100px, 5%);

}
</style>
