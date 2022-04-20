import {BaseService} from '@/api/BaseService.js';

class AuthService extends BaseService {
    constructor({ baseUrl }) {
        super({ baseUrl });
    }

    async login({ username, password }) {
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: {
                Authorization: `Basic ${btoa(`${username}:${password}`)}`,
            },
        });
        return await response.json();
    }

    async logout() {
        await fetch('/api/logout', { method: 'POST' });
    }
}

export default new AuthService({ baseUrl: '/auth' });
