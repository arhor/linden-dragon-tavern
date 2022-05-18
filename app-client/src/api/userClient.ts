import client from '@/api/client.js';

export type User = {
    authorities: string[];
};

export async function login(username: string, password: string): Promise<User> {
    const { data } = await client.post('/api/login', null, {
        headers: {
            Authorization: `Basic ${btoa(`${username}:${password}`)}`,
        },
    });
    return data;
}

export async function logout(): Promise<void> {
    await client.post('/api/logout');
}
