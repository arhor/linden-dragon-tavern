import { BaseService } from '@/api/BaseService.js';

class AccountService extends BaseService {
    constructor({ baseUrl }) {
        super({ baseUrl });
    }

    async createNewAccount({ username, password, email, firstName, lastName }) {
        const { headers } = await this.http.post({
            username,
            password,
            email,
            firstName,
            lastName,
        });
        console.log(headers['Location']);
    }
}

export default new AccountService({ baseUrl: '/api/v1/accounts' });
