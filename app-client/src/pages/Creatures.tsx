import { useEffect, useState } from 'react';

import log from 'loglevel';
import { observer } from 'mobx-react';

import { getAllCreatures } from "@/api/creatureClient";
import CreatureList from '@/components/CreatureList';
import { Creature } from '@/generated/dnd/Creature';
import { useStore } from '@/store';

const Creatures = () => {
    const { notification } = useStore();
    const [creatures, setCreatures] = useState<Creature[]>([]);

    const handleError = (message: string, error: unknown) => {
        log.error(message, error);
        notification.enqueue({ level: 'error', message: message });
    };


    useEffect(
        () => {
            getAllCreatures()
                .then(items => {
                    setCreatures(items);
                })
                .catch(error => {
                    handleError('Cannot retrieve creatures list', error);
                });
        },
        []
    );

    return (
        <CreatureList items={creatures} />
    );
};

export default observer(Creatures);
