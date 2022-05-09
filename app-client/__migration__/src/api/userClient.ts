import client from '@/api/client.js';

export async function login(username: string, password: string) {
    const { data } = await client.post('/api/login', null, {
        headers: {
            Authorization: `Basic ${btoa(`${username}:${password}`)}`,
        },
    });
    return data;
}

export async function logout() {
    await client.post('/api/logout');
}
