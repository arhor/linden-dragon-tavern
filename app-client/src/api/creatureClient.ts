import client from '@/api/client';
import { Creature } from '@/generated/dnd/Creature';

const basePath = 'v1/creatures';

export async function getAllCreatures(): Promise<Creature[]> {
    const { data } = await client.get(basePath);
    return data.items;
}

export async function getCreatureByName(name: string): Promise<Creature> {
    const { data } = await client.get(`${basePath}/${name}`);
    return data;
}
