import { useEffect, useState } from 'react';

import { getAllCreatures } from "@/api/creatureClient";
import CreatureList from '@/components/CreatureList';
import { Creature } from '@/generated/dnd/Creature';

const About = () => {
    const [creatures, setCreatures] = useState<Creature[]>([])

    useEffect(
        () => {
            getAllCreatures().then(creatures => {
                if (creatures) {
                    setCreatures(creatures);
                }
            });
        },
        []
    );

    return (
        <CreatureList items={creatures} />
    );
};

export default About;
