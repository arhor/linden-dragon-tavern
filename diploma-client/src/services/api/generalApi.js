import axios from 'axios';
import { requestInterceptor } from '@/services/api/interceptors/requestInterceptor';

const generalApi = axios.create({
    baseURL: 'http://localhost:8080',
    headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
        Cache: 'no-cache',
    },
});

generalApi.interceptors.request.use(requestInterceptor);

export default generalApi;
