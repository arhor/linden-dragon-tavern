import { action, observable, makeObservable } from 'mobx';

export default class UserStore {
    authenticated = false;
    authorities: string[] = [];

    constructor() {
        makeObservable(this, {
            authenticated: observable,
            authorities: observable,
            setAuthenticated: action.bound,
            setAuthorities: action.bound,
        });
    }

    setAuthenticated(authenticated: boolean) {
        this.authenticated = authenticated;
    }

    setAuthorities(authorities: string[]) {
        this.authorities = authorities;
    }

    hasAuthorities(authorities: string[]): boolean {
        if (this.authorities.length !== 0) {
            if (authorities.length === 0) {
                return true;
            }
            return authorities.every((auth) => this.authorities.includes(auth));
        }
        return authorities.length === 0;
    }
}
