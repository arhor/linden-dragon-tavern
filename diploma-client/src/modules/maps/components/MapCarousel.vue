<template>
    <v-carousel>
        <v-carousel-item v-for="(map, i) in Barovia" :key="i" :src="map.src" contain />
    </v-carousel>
</template>

<script>
import { mapState } from 'vuex';

export default {
    name: 'MapCarousel',
    computed: {
        ...mapState('maps', ['Barovia'])
    }
};
</script>
