import { action, observable, makeObservable, runInAction } from 'mobx';

import { login, logout } from '@/api/userClient';

export default class UserStore {
    username: string | null = null;
    authenticated = false;
    authorities: string[] = [];

    constructor() {
        makeObservable(this, { 
            username: observable,
            authenticated: observable,
            authorities: observable,
            setAuthenticated: action.bound,
            setAuthorities: action.bound,
            setSessionData: action.bound,
            invalidateSession: action.bound,
            signIn: action.bound,
            signOut: action.bound,
        });
    }

    setAuthenticated(authenticated: boolean): void {
        this.authenticated = authenticated;
    }

    setAuthorities(authorities: string[]): void {
        this.authorities = authorities;
    }

    setSessionData(username: string, authorities: string[]): void {
        this.username = username;
        this.authorities = authorities;
        this.authenticated = true;
    }

    invalidateSession(): void {
        this.username = null;
        this.authorities = [];
        this.authenticated = false;
    }

    async signIn(username: string, password: string): Promise<boolean> {
        try {
            const { authorities } = await login(username, password);
            runInAction(() => this.setSessionData(username, authorities));
            return true;
        } catch (e) {
            runInAction(this.invalidateSession);
            return false;
        }
    }

    async signOut(): Promise<void> {
        await logout();
        runInAction(this.invalidateSession);
    }
}
