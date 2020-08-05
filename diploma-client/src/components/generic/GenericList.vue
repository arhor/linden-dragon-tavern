<template>
    <v-row v-if="notEmpty">
        <v-col cols="12" class="text-xs-left">
            <strong v-if="title">{{ title + ':' }}</strong> {{ commaSeparatedString }}
        </v-col>
    </v-row>
</template>

<script>
import { commaSeparate, isEmptyArray } from '@/utils/ArrayUtils.js';

export default {
    name: 'GenericList',
    props: {
        title: {
            type: String,
            default: null
        },
        items: {
            type: Array,
            default: () => []
        }
    },
    computed: {
        notEmpty() {
            return !isEmptyArray(this.items);
        },
        commaSeparatedString() {
            return commaSeparate(this.items);
        }
    }
};
</script>
