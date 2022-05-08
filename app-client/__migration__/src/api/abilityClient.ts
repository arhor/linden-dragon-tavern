import axios from '@/api/client.js';

const basePath = '/abilities';

export async function getAllAbilities() {
    return await axios.get(basePath);
}
