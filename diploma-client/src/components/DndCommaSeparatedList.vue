<template functional>
    <v-row v-if="$options.notEmpty(props.items)">
        <v-col cols="12" class="text-xs-left">
            <strong v-if="props.title">{{ props.title + ':' }}</strong> {{ props.items.join(', ') }}
        </v-col>
    </v-row>
</template>

<script>
import { isEmptyArray } from '@/utils/arrayUtils.js';
import { refExists } from '@/utils/coreUtils';

export default {
    name: 'DndCommaSeparatedList',
    props: {
        title: {
            type: String,
            default: null,
        },
        items: {
            type: Array,
            default: () => [],
        },
    },
    notEmpty: (items) => refExists(items) && !isEmptyArray(items),
};
</script>
