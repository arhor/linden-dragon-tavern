import axios from '@/api/client.js';

export class BaseService {
    private baseUrl: string;

    constructor(baseUrl: string) {
        this.baseUrl = baseUrl;
    }
}

export default axios;
