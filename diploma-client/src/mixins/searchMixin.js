export const searchMixin = {
    data: (self) => ({
        search: self.$route.query.search ?? '',
    }),

    watch: {
        search(value) {
            this.$router.push({
                path: this.$route.path,
                query: value ? { search: value } : {},
            });
        },
    },
};
