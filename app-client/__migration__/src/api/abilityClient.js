import axios from '@/api/client.js';

const basePath = '/abilities';

export async function getAllAbilities() {
    const abilities = await axios.get(basePath);
    return abilities;
}
