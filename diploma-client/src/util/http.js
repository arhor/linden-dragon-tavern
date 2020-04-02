import Vue from 'vue'
import VueCookies from 'vue-cookies'
import axios from 'axios'
import uuid from '@/util/uuid.js'

Vue.use(VueCookies)

const csrfToken = uuid()

Vue.$cookies.set('csrf-token', csrfToken)

const http = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json',
    'Cache': 'no-cache',
  }
})

const safeMethods = ['get', 'head', 'options', 'trace']

http.interceptors.request.use(
  req => {
    if (!safeMethods.includes(req.method)) {
      req.headers['x-csrf-token'] = csrfToken
    }
    return req
  },
  err => {
    Promise.reject(err)
})

export default http