import axios from 'axios';

import { requestInterceptor } from '@/api/interceptors';

const client = axios.create({
    baseURL: '/api',
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Cache': 'no-cache',
    },
});

client.interceptors.request.use(requestInterceptor);

export default client;
