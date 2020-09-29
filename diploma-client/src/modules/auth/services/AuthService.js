import { BaseService } from '@/api/BaseService';
import { SERVER_API_URL } from '@/config/server-api';
import { parseJWT } from '@/utils/coreUtils';

class AuthService extends BaseService {
    constructor({ baseUrl }) {
        super({ baseUrl });
    }

    async signIn({ username, password }) {
        const { data } = await this.http.post(`${SERVER_API_URL}/auth/token`, {
            username,
            password,
        });

        const {
            sub: { accessToken, tokenType },
        } = parseJWT(data);

        return { accessToken, tokenType };
    }
}

export default new AuthService({ baseUrl: '/auth' });
