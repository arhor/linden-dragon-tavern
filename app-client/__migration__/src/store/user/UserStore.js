import { makeAutoObservable } from 'mobx';

export default class UserStore {
    /** @type {boolean} */
    authenticated = false;

    /** @type {string[]} */
    authorities = [];

    constructor() {
        makeAutoObservable(this, null, { autoBind: true });
    }

    /**
     * @param {boolean} authenticated
     */
    setAuthenticated(authenticated) {
        this.authenticated = authenticated;
    }

    /**
     * @param {string[]} authorities
     */
    setAuthorities(authorities) {
        this.authorities = authorities;
    }

    /**
     * @param {string[]} [authorities]
     * @returns {boolean}
     */
    hasAuthorities(authorities = []) {
        if (this.authorities.length !== 0) {
            if (authorities.length === 0) {
                return true;
            }
            return authorities.every((auth) => this.authorities.includes(auth));
        }
        return authorities.length === 0;
    }
}
