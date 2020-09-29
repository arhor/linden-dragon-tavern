import axios, { BaseService } from '@/api/BaseService.js';

class SpellService extends BaseService {
    constructor({ baseUrl }) {
        super({ baseUrl });
    }

    async getAllSpells() {
        const { data } = await axios.get('/api/v1/spells');
        return data;
    }

    async getSpellByName(name) {
        const { data } = await axios.get(`/api/v1/spells/${name}/details`);
        return data;
    }
}

export default new SpellService({ baseUrl: '/api/v1/spells' });
