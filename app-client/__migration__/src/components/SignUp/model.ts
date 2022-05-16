import { REG_EXP_ALPHANUMERIC, REG_EXP_EMAIL } from '@/utils/patterns';
import { defineValidator } from '@/utils/validationUtils';

export type Values = {
    username: string,
    password: string,
    email: string,
    firstName: string,
    lastName: string,
};

export const validator = defineValidator<Values>({
    username: [
        (v) => !!v
            || 'Username is required',
        (v) => (v && v.length >= 6)
            || 'Username must be longer than 6 characters',
        (v) => (v && v.length <= 20)
            || 'Username must be less than 20 characters',
        (v) => (v && REG_EXP_ALPHANUMERIC.test(v))
            || 'username must contain only letters and numbers',
    ],
    password: [
        (v) => !!v
            || 'Password is required',
        (v) => (v && v.length <= 10)
            || 'Password must be less than 10 characters',
    ],
    email: [
        (v) => !!v
            || 'E-mail is required',
        (v) => (v && REG_EXP_EMAIL.test(v))
            || 'E-mail must be valid'
    ],
    firstName: [
        (v) => (v && v.length <= 10)
            || 'Name must be less than 10 characters',
    ],
    lastName: [
        (v) => (v && v.length <= 10)
            || 'Name must be less than 10 characters',
    ],
});
