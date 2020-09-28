<template>
    <v-breadcrumbs :items="breadcrumbs">
        <template v-slot:divider>
            <v-icon>mdi-chevron-right</v-icon>
        </template>
        <template v-slot:item="{ item }">
            <v-breadcrumbs-item :to="item.to" :disabled="item.disabled">
                {{ item.text.toUpperCase() }}
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
            this.breadcrumbs = [];
            this.$route.meta?.breadcrumbs?.forEach((breadcrumb, idx, items) => {
                this.breadcrumbs.push({
                    ...breadcrumb,
                    disabled: Boolean(idx === items.length - 1),
                });
            });
        },
    },
    watch: {
        $route: function() {
            this.updateBreadcrumbs();
        },
    },
    mounted() {
        this.updateBreadcrumbs();
    },
};
</script>
