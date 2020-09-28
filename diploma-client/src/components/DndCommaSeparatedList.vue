<template>
    <v-row v-if="notEmpty">
        <v-col cols="12" class="text-xs-left">
            <strong v-if="title">{{ title + ':' }}</strong> {{ items.join(', ') }}
        </v-col>
    </v-row>
</template>

<script>
import { isEmptyArray } from '@/utils/arrayUtils';

export default {
    name: 'DndCommaSeparatedList',
    props: {
        title: {
            type: String,
            default: null,
        },
        items: {
            type: Array,
            required: true,
        },
    },
    computed: {
        notEmpty() {
            return !isEmptyArray(this.items);
        },
    },
};
</script>
