import { Field, Form, Formik } from 'formik';
import { TextField } from 'formik-mui';
import { FormikHelpers } from 'formik/dist/types';
import { Link as RouterLink } from 'react-router-dom';

import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Avatar from '@mui/material/Avatar';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import LinearProgress from '@mui/material/LinearProgress';
import Link from '@mui/material/Link';
import Typography from '@mui/material/Typography';

import { SignUpRequest } from '@/generated/SignUpRequest';
import { REG_EXP_EMAIL } from '@/utils/patterns';
import { defineValidator } from '@/utils/validationUtils';

const validator = defineValidator<SignUpRequest>({
    email: [
        (v) => !!v
            || 'E-mail is required',
        (v) => (v && REG_EXP_EMAIL.test(v))
            || 'E-mail must be valid'
    ],
    password: [
        (v) => !!v
            || 'Password is required',
        (v) => (v && v.length <= 10)
            || 'Password must be less than 10 characters',
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

const SignUpForm = () => {
    const initialValues: SignUpRequest = {
        email: '',
        password: '',
        firstName: '',
        lastName: '',
    };

    const handleSubmit = (values: SignUpRequest, { setSubmitting }: FormikHelpers<SignUpRequest>) => {
        setTimeout(() => {
            setSubmitting(false);
            alert(JSON.stringify(values, null, 2));
        }, 3000);
    };

    return (
        <Box
            sx={{
                marginTop: 8,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
            }}
        >
            <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
                Sign up
            </Typography>
            <Formik initialValues={initialValues} onSubmit={handleSubmit} validate={validator}>
                {({ submitForm, isSubmitting }) => (
                    <Form>
                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={6}>
                                <Field
                                    component={TextField}
                                    autoComplete="given-name"
                                    name="firstName"
                                    required
                                    fullWidth
                                    id="firstName"
                                    label="First Name"
                                    autoFocus
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <Field
                                    component={TextField}
                                    required
                                    fullWidth
                                    id="lastName"
                                    label="Last Name"
                                    name="lastName"
                                    autoComplete="family-name"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <Field
                                    component={TextField}
                                    required
                                    fullWidth
                                    id="email"
                                    label="Email Address"
                                    name="email"
                                    autoComplete="email"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <Field
                                    component={TextField}
                                    required
                                    fullWidth
                                    name="password"
                                    label="Password"
                                    type="password"
                                    id="password"
                                    autoComplete="new-password"
                                />
                            </Grid>
                        </Grid>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{ mt: 3, mb: 2 }}
                            onClick={submitForm}
                        >
                            Sign Up
                        </Button>
                        {isSubmitting && <LinearProgress />}
                        <Grid container justifyContent="flex-end">
                            <Grid item>
                                <Link to="/sign-in" component={RouterLink} variant="body2">
                                    Already have an account? Sign in
                                </Link>
                            </Grid>
                        </Grid>
                    </Form>
                )}
            </Formik>
        </Box>
    );
};

export default SignUpForm;
