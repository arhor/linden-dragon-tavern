import { action, makeObservable, observable, runInAction } from 'mobx';
import { getAllSpells } from '@/api/spellClient';
import log from 'loglevel';

export default class SpellStore {
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
            const spells = await getAllSpells();
            runInAction(() => {
                this.items = spells;
                this.loaded = true;
            });
        } catch (e) {
            log.error('Unable to load spells.', e);
        }
    }
}
