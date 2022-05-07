import React from 'react';

import MainAbilityCounter, { ABILITY } from '@/components/MainAbilityCounter';
import StatelessWidget, { SIZE, TYPE } from '@/components/StatelessWidget';

function Home() {
    return (
        <>
            <StatelessWidget type={TYPE.PAGE} size={SIZE.LARGE} title="Home page"/>
            <MainAbilityCounter  value={20} name={ABILITY.CHA}/>
        </>
    );
}

export default Home;
