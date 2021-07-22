export default {
    computed: {
        stepData() {
            return {};
        },
    },

    methods: {
        emitDataChanges() {
            console.log('emitDataChanges');
            console.log(this.stepData);
            this.$emit('data-changed', this.stepData);
        },
    },
};
