<template>
    <div class="counter">
        <span class="counter_type">{{ type }}</span>
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

.counter::before {
    content: '';
    display: block;
    height: 45px;
    border: solid;
    border-bottom: transparent;
    border-left: transparent;
    border-radius: 50%;
    transform: rotate(-45deg);
    margin-bottom: -33px;
}

.counter_count {
    color: white;
}
</style>
