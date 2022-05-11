import { Field, Form, Formik } from 'formik';
import { TextField } from 'formik-mui';
import { FormikHelpers } from 'formik/dist/types';

import { Button, LinearProgress } from '@mui/material';

import { REG_EXP_ALPHANUMERIC, REG_EXP_EMAIL } from '@/utils/patterns';
import { defineValidator } from '@/utils/validationUtils';

type Values = {
    username: string,
    password: string,
    email: string,
    firstName: string,
    lastName: string,
};

const validator = defineValidator<Values>({
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

export type Props = {
    onSwitch: () => void;
};

const SignUp = ({ onSwitch }: Props) => {
    const initialValues: Values = {
        username: '',
        password: '',
        email: '',
        firstName: '',
        lastName: '',
    };
    
    const handleSubmit = (values: Values, { setSubmitting }: FormikHelpers<Values>) => {
        setTimeout(() => {
            setSubmitting(false);
            alert(JSON.stringify(values, null, 2));
        }, 3000);
    };
    
    return (
        <Formik initialValues={initialValues} onSubmit={handleSubmit} validate={validator}>
            {({ submitForm, isSubmitting }) => (
                <Form>
                    <Field component={TextField} name="username" type="text" label="Username" />
                    <br />
                    <Field component={TextField} type="password" label="Password" name="password" />
                    <br />
                    <Field component={TextField} name="email" type="email" label="Email" />
                    <br />
                    <Field component={TextField} name="firstName" type="text" label="First Name" />
                    <br />
                    <Field component={TextField} name="lastName" type="text" label="Last Name" />
                    <br />
                    {isSubmitting && <LinearProgress />}
                    <br />
                    <Button onClick={submitForm} disabled={isSubmitting}>
                        Submit
                    </Button>
                    <Button onClick={onSwitch}>
                        Account already exists
                    </Button>
                </Form>
            )}
        </Formik>
    );
};

export default SignUp;
