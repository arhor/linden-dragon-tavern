import { fileURLToPath, URL } from 'url';

import react from '@vitejs/plugin-react';
import { defineConfig, loadEnv } from 'vite';
import eslint from 'vite-plugin-eslint';

export default defineConfig(({ mode }) => {
    const resolvePath = (path: string) => fileURLToPath(new URL(path, import.meta.url));

    const rootProjectDir = resolvePath('..');
    const variablePrefixes = [''];

    process.env = { ...loadEnv(mode, rootProjectDir, variablePrefixes) };

    return {
        plugins: [react(), eslint()],
        resolve: {
            alias: {
                '@': resolvePath('src'),
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
            setupFiles: ['src/tests.setup.ts'],
        },
    }
});
