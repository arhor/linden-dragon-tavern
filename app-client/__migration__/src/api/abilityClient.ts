import client from '@/api/client.js';

const basePath = '/abilities';

export type Ability = {
    name: string;
};

export async function getAllAbilities(): Promise<Ability[]> {
    return (await client.get(basePath)).data.items;
}
