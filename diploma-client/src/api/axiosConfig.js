import axios from 'axios';
import { SERVER_API_URL } from '@/api/server-api.js';
import { requestInterceptor } from '@/api/interceptors';

// TODO:
//  preconfigure global axios instance to use, any axios import must use this file as instance
//  source, since IDEA has intellisense only for api calls from object called `axios` =_=

axios['defaults'].baseURL = SERVER_API_URL;

axios['defaults'].headers.common['Accept'] = 'application/json';
axios['defaults'].headers.common['Content-Type'] = 'application/json';
axios['defaults'].headers.common['Cache'] = 'no-cache';

axios['interceptors'].request.use(requestInterceptor);

export default axios;
