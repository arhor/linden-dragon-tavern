import axios from '@/api/client.js';

export class BaseService {
    /** 
     * @param {string} baseUrl
     */
    constructor(baseUrl) {
        this.baseUrl = baseUrl;
    }
}

export default axios;
