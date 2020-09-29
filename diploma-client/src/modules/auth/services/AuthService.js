import { BaseService } from '@/api/BaseService.js';
import { SERVER_API_URL } from '@/config/server-api.js';
import { parseJWT } from '@/utils/coreUtils.js';

const convertResponseToAuthModel = (data) => {
    const {
        sub: { accessToken, tokenType },
    } = parseJWT(data);

    return { accessToken, tokenType };
};

class AuthService extends BaseService {
    constructor({ baseUrl }) {
        super({ baseUrl });
    }

    async signIn({ username, password }) {
        const { data } = await this.http.post(`${SERVER_API_URL}/token`, {
            username,
            password,
        });
        return convertResponseToAuthModel(data);
    }

    async signUp({ username, password }) {
        const { data } = await this.http.post(`${this.baseUrl}/register`, {
            username,
            password,
        });
        return convertResponseToAuthModel(data);
    }

    async refresh() {
        const { data } = await this.http.get(`${this.baseUrl}/refresh`);
        return convertResponseToAuthModel(data);
    }
}

export default new AuthService({ baseUrl: '/auth' });
