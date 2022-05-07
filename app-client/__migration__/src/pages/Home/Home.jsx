import React from 'react';

import StatelessWidget, { SIZE, TYPE } from '@/components/StatelessWidget';

function Home() {
    return (
        <StatelessWidget type={TYPE.PAGE} size={SIZE.LARGE} title="Home page"/>
    );
}

export default Home;
