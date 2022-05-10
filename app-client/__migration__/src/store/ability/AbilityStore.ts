import log from 'loglevel';
import { action, makeObservable, observable, runInAction } from 'mobx';

import { getAllAbilities } from '@/api/abilityClient';

export default class AbilityStore {
    items: any[] = [];
    loaded = false;

    constructor() {
        makeObservable(this, {
            items: observable,
            loaded: observable,
            load: action.bound,
        });
    }

    async load() {
        if (this.loaded) {
            return;
        }
        try {
            const abilities = await getAllAbilities();
            runInAction(() => {
                this.items = abilities;
                this.loaded = true;
            });
        } catch (e) {
            log.error('Unable to load abilities.', e);
        }
    }
}
