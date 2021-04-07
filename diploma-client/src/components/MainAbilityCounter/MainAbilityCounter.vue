<template>
    <div class="counter">
        <div v-if="modificator" class="counter_modificator">{{ modificator }}</div>
        <span class="counter_type">{{ processType(type) }}</span>
        <span class="counter_count">{{ count }}</span>
    </div>
</template>

<script>
export default {
    name: 'MainAbilityCounter',
    props: {
        type: {
            type: String,
            required: true,
        },
        count: {
            type: Number,
            requred: true,
        },
        modificator: {
            type: String,
        },
    },
    methods: {
        processType(type) {
            return type.toUpperCase().slice(0, 3);
        },
    },
};
</script>

<style scoped>
.counter {
    display: flex;
    flex-direction: column;
    text-align: center;
    width: 50px;
    font-weight: 900;
    color: #eadeb8;
}

.counter_type::before {
    content: '';
    display: block;
    height: 45px;
    border: solid #988962;
    border-bottom: transparent;
    border-left: transparent;
    border-radius: 50%;
    transform: rotate(-45deg);
    margin-bottom: -33px;
    margin-top: -3px;
}

.counter_count {
    color: white;
}

.counter_modificator {
    background: no-repeat url('../../assets/svg/horns.svg') bottom;
    width: 50px;
    height: 35px;
    margin-right: 3px;
    background-size: 100%;
    font-size: larger;
}
</style>
