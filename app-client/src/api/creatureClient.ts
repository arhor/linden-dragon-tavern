import log from 'loglevel';

import client from '@/api/client';
import { Creature } from '@/generated/dnd/Creature';
import { store } from '@/store';
import { Optional } from '@/utils/core-utils';

const basePath = 'v1/creatures';

export async function getAllCreatures(): Promise<Optional<Creature[]>> {
    try {
        const { data } = await client.get(basePath);
        return data.items;
    } catch (e) {
        handleError('Cannot retrieve creatures list', e);
        return null;
    }
}

export async function getCreatureByName(name: string): Promise<Optional<Creature>> {
    try {
        const { data } = await client.get(`${basePath}/${name}`);
        return data.items;
    } catch (e) {
        handleError('Cannot retrieve creature by name', e);
        return null;
    }
}

function handleError(message: string, error: unknown) {
    log.error(message, error);
    store.notification.enqueue({ level: 'error', message: message });
}
