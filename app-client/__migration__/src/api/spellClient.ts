import axios from '@/api/client.js';

const basePath = '/spells';

export async function getAllSpells() {
    return (await axios.get(basePath)).data.items;
}

export async function getSpellByName(name: string) {
    return (await axios.get(`${basePath}/${name}`)).data;
}
