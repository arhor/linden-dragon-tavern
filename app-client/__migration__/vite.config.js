/// <reference types="vitest" />
/// <reference types="vite/client" />

import react from '@vitejs/plugin-react';
import eslint from 'vite-plugin-eslint';
import { defineConfig } from 'vite';
import { URL, fileURLToPath } from 'url';

export default defineConfig({
    plugins: [react(), eslint()],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('src', import.meta.url)),
        },
    },
    server: {
        proxy: {
            '^/api': {
                target: process.env.API_BASE_URL,
                changeOrigin: true,
            },
        },
    },
    test: {
        globals: true,
        environment: 'jsdom',
        watch: false,
        setupFiles: ['tests.setup.js'],
    },
});