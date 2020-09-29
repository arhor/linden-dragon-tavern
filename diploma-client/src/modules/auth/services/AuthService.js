import axios, { BaseService } from '@/api/BaseService.js';
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
        const { data } = await axios.post('/auth/token', {
            username,
            password,
        });
        return convertResponseToAuthModel(data);
    }

    async signUp({ username, password }) {
        const { data } = await axios.post('/auth/register', {
            username,
            password,
        });
        return convertResponseToAuthModel(data);
    }

    async refresh() {
        const { data } = await axios.get('/auth/refresh');
        return convertResponseToAuthModel(data);
    }
}

export default new AuthService({ baseUrl: '/auth' });
