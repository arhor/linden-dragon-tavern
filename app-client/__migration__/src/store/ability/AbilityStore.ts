import log from 'loglevel';
import { flow, makeObservable, observable } from 'mobx';

import { Ability, getAllAbilities } from '@/api/abilityClient';

export default class AbilityStore {
    items: Ability[] = [];
    loaded = false;

    constructor() {
        makeObservable(this, {
            items: observable,
            loaded: observable,
            load: flow.bound,
        });
    }

    * load(): Generator<Promise<Ability[]>, void, Ability[]> {
        if (this.loaded) {
            return;
        }
        try {
            this.items = yield getAllAbilities();
            this.loaded = true;
        } catch (e) {
            log.error('Unable to load abilities.', e);
        }
    }
}
