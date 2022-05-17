import { Field, Form, Formik } from 'formik';
import { TextField } from 'formik-mui';
import { FormikHelpers } from 'formik/dist/types';

import { Button, LinearProgress } from '@mui/material';

import { Values, validator } from '@/components/SignUp/model';

export type Props = {
    onSwitch: () => void;
};

const SignUp = ({ onSwitch }: Props) => {    
    const handleSubmit = (values: Values, { setSubmitting }: FormikHelpers<Values>) => {
        setTimeout(() => {
            setSubmitting(false);
            alert(JSON.stringify(values, null, 2));
        }, 3000);
    };
    
    return (
        <Formik initialValues={{
            username: '',
            password: '',
            email: '',
            firstName: '',
            lastName: '',
        }} onSubmit={handleSubmit} validate={validator}>
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
