<template>
    <div class="counter">
        <div v-if="modifier" class="modifier">{{ modifier }}</div>
        <span class="type">{{ typeDisplayName }}</span>
        <span class="count">{{ count }}</span>
    </div>
</template>

<script>
import { computed } from '@vue/composition-api';
import { calculateModifier, minifyAbilityName } from '@/utils/abilityUtils';

export default {
    name: 'MainAbilityCounter',

    props: {
        type: {
            type: String,
            required: true,
        },
        count: {
            type: Number,
            required: true,
        },
    },

    setup(props) {
        return {
            typeDisplayName: computed(() => minifyAbilityName(props.type)),
            modifier: computed(() => calculateModifier(props.count)),
        };
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

.counter .type::before {
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

.counter .count {
    color: white;
}

.counter .modifier {
    background: no-repeat url('~@/assets/svg/horns.svg') bottom;
    width: 50px;
    height: 35px;
    margin-right: 3px;
    background-size: 100%;
    font-size: larger;
}
</style>
