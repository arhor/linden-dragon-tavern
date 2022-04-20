import axios from 'axios';

import { SERVER_API_URL } from '@/api/server-api.js';
import { requestInterceptor } from '@/api/interceptors';

const generalApi = axios.create({
    baseURL: SERVER_API_URL,
    headers: {
        ['Accept']: 'application/json',
        ['Content-Type']: 'application/json',
        ['Cache']: 'no-cache',
    },
});

generalApi['interceptors'].request.use(requestInterceptor);

export default generalApi;
