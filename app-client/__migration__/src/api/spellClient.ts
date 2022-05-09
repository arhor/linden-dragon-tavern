import client from '@/api/client.js';

const basePath = '/spells';

export async function getAllSpells() {
    return (await client.get(basePath)).data.items;
}

export async function getSpellByName(name: string) {
    return (await client.get(`${basePath}/${name}`)).data;
}
