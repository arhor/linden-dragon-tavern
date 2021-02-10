<template>
    <v-breadcrumbs :items="breadcrumbs">
        <template v-slot:divider>
            <v-icon>mdi-chevron-right</v-icon>
        </template>
        <template v-slot:item="{ item }">
            <v-breadcrumbs-item :to="item.link" :disabled="item.disabled">
                {{ $t(item.label).toUpperCase() }}
            </v-breadcrumbs-item>
        </template>
    </v-breadcrumbs>
</template>

<script>
export default {
    name: 'DndBreadcrumbs',
    data: () => ({
        breadcrumbs: [],
    }),
    methods: {
        updateBreadcrumbs() {
            this.breadcrumbs =
                this.$route.meta?.breadcrumbs?.map((breadcrumb, idx, items) => {
                    return {
                        ...breadcrumb,
                        disabled: Boolean(idx === items.length - 1),
                    };
                }) ?? [];
        },
    },
    watch: {
        $route() {
            this.updateBreadcrumbs();
        },
    },
    mounted() {
        this.updateBreadcrumbs();
    },
};
</script>
