import { SERVER_API_URL } from '@/config/server-api';

export class BaseService {
    constructor({ baseUrl }) {
        this.baseUrl = `${SERVER_API_URL}${baseUrl}`;
    }
}
