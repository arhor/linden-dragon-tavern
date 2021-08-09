const TIMEOUT_BEFORE_SEARCH_REQUEST_MILLIS = 1000;

// TODO: add additional `search` field which should be used for delayed search requests
export const searchMixin = {
    data: (self) => ({
        search: self.$route.query.search ?? '',
        timeoutId: null,
    }),

    watch: {
        search(value) {
            if (this.timeoutId != null) {
                clearTimeout(this.timeoutId);
            }
            this.timeoutId = setTimeout(() => {
                const { path } = this.$route;
                const query = value ? { search: value } : {};
                this.$router.push({ path, query });
                this.options = { ...this.options, search: value };
                this.timeoutId = null;
            }, TIMEOUT_BEFORE_SEARCH_REQUEST_MILLIS);
        },
    },
};
