<template>
    <dnd-sign-in v-if="isSignIn" @sign-up="status = 'sign-up'" />
    <dnd-sign-up v-else @sign-in="status = 'sign-in'" />
</template>

<script>
import DndSignIn from '@/modules/auth/components/DndSignIn.vue';
import DndSignUp from '@/modules/auth/components/DndSignUp.vue';

export default {
    components: { DndSignIn, DndSignUp },
    data: () => ({
        status: 'sign-in',
    }),
    computed: {
        isSignIn() {
            return this.status === 'sign-in';
        },
    },
};
</script>
