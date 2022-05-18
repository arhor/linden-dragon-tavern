import { useState } from 'react';

import SignIn from '@/components/SignIn';
import SignUp from '@/components/SignUp';

export type Props = {
    onLoggedIn: () => void;
};

const Auth = ({ onLoggedIn }: Props) => {
    const [status, setStatus] = useState<'sign-up' | 'sign-in'>('sign-up');

    return status === 'sign-in'
        ? <SignIn onSwitch={() => setStatus('sign-up')} onLoggedIn={onLoggedIn} />
        : <SignUp onSwitch={() => setStatus('sign-in')} />;
};

export default Auth;
