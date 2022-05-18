import { useState } from 'react';

import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';

import { useStore } from '@/store';

export type Props = {
    onSwitch: () => void;
    onLoggedIn: () => void;
};

const SignIn = ({ onSwitch, onLoggedIn }: Props) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(false);

    const { user } = useStore();

    const trySignIn = async () => {
        setError(false);
        const success = await user.signIn(username, password);
        if (success) {
            setUsername('');
            setPassword('');
            onLoggedIn();
        } else {
            setError(true);
        }
    };

    return (
        <Box component="form" noValidate autoComplete="off">
            {error && <div>Username or Password is incorrect</div>}
            <Typography variant="h5">
                Login Form
            </Typography>
            <TextField
                id="username"
                label="Username"
                required
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <TextField
                id="password"
                label="Password"
                required
                type="password"
                autoComplete="current-password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <Button onClick={() => trySignIn()}>
                Sign In
            </Button>
            <Button onClick={() => onSwitch()}>
                Register
            </Button>
        </Box>
    );
};

export default SignIn;
