import axios from '@/api/generalApi.js';

export class BaseService {
    constructor({ baseUrl }) {
        this.baseUrl = baseUrl;
    }
}

export default axios;
