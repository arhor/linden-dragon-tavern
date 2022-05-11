import { Field, Form, Formik } from 'formik';
import { TextField } from 'formik-mui';

import { Button, LinearProgress } from '@mui/material';

import { defineValidator } from '@/utils/validationUtils';

export type Props = {
    onSwitch: () => void;
};

const validator = defineValidator({
    username: [
        (v) => !!v
            || 'Username is required',
        (v) => (v && v.length >= 6)
            || 'Username must be longer than 6 characters',
        (v) => (v && v.length <= 20)
            || 'Username must be less than 20 characters',
        (v) => (v && /^[a-zA-Z0-9]*$/.test(v))
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
        (v) => (v && /.+@.+\..+/.test(v))
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

const SignUp = ({ onSwitch }: Props) => {
    return (
        <Formik
            initialValues={{
                username: '',
                password: '',
                email: '',
                firstName: '',
                lastName: '',
            }}
            validate={(values) => validator(values)}
            onSubmit={(values, { setSubmitting }) => {
                setTimeout(() => {
                    setSubmitting(false);
                    alert(JSON.stringify(values, null, 2));
                }, 3000);
            }}
        >
            {({ submitForm, isSubmitting }) => (
                <Form>
                    <Field
                        component={TextField}
                        name="username"
                        type="text"
                        label="Username"
                    />
                    <br />
                    <Field
                        component={TextField}
                        type="password"
                        label="Password"
                        name="password"
                    />
                    <br />
                    <Field
                        component={TextField}
                        name="email"
                        type="email"
                        label="Email"
                    />
                    <br />
                    <Field
                        component={TextField}
                        name="firstName"
                        type="text"
                        label="First Name"
                    />
                    <br />
                    <Field
                        component={TextField}
                        name="lastName"
                        type="text"
                        label="Last Name"
                    />
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
