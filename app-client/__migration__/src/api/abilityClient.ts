import client from '@/api/client.js';

const basePath = '/abilities';

export async function getAllAbilities() {
    return (await client.get(basePath)).data.items;
}
