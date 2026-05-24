// Purpose:
// Create a pre‑configured Axios client that automatically attaches the JWT (from localStorage)
// to every outgoing request. This avoids manually adding headers in each API call.

import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8080/api',
});

// Request interceptor – runs before every request
instance.interceptors.request.use(
  (config) => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user?.token) {
      config.headers.Authorization = `Bearer ${user.token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default instance;