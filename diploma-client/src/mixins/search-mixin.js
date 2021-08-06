export const createSearchMixin = (path) => ({
    data: (self) => ({
        search: self.$route.query.search ?? '',
    }),

    watch: {
        search(value) {
            this.$router.push({ path, query: value ? { search: value } : {} });
        },
    },
});
