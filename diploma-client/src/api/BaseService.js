import axios from '@/api/axiosConfig.js';

export class BaseService {
    constructor({ baseUrl }) {
        this.baseUrl = baseUrl;
    }
}

export default axios;
