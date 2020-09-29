import { BaseService } from '@/api/BaseService';

class SpellService extends BaseService {
    constructor({ baseUrl }) {
        super({ baseUrl });
    }

    async getAllSpells() {
        const { data } = await this.http.get(this.baseUrl);
        return data;
    }

    async getSpellByName(name) {
        const { data } = await this.http.get(`${this.baseUrl}/${name}/details`);
        return data;
    }
}

export default new SpellService({ baseUrl: '/api/v1/spells' });
