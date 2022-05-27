import log from 'loglevel';
import { flow, makeObservable, observable } from 'mobx';

import { getAllCreatures } from '@/api/creatureClient';
import { Creature } from '@/generated/dnd/Creature';
import { store } from '@/store';

const handleError = (message: string, error: unknown) => {
    log.error(message, error);
    store.notification.enqueue({ level: 'error', message: message });
};

export default class CreatureStore {
    items: Creature[] = [];
    loaded = false;
    loading = false;

    constructor() {
        makeObservable(this, {
            items: observable,
            loaded: observable,
            loading: observable,
            load: flow.bound,
        });
    }

    * load(): Generator<Promise<Creature[]>, void, Creature[]> {
        if (this.loaded) {
            return;
        }
        this.loading = true;
        try {
            this.items = yield getAllCreatures();
            this.loaded = true;
        } catch (e) {
            handleError('Unable to load creatures.', e);
        } finally {
            this.loading = false;
        }
    }
}
