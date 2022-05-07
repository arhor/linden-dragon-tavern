import { action, makeObservable, observable } from 'mobx';

import { generateUUID } from '@/utils/coreUtils';

export default class NotificationStore {
    items = [];

    constructor() {
        makeObservable(this, {
            items: observable,
            enqueue: action.bound,
            remove: action.bound,
        });
    }

    enqueue(item) {
        this.items.push({
            id: generateUUID(),
            ...item,
        });
    }

    remove(...ids) {
        if (ids?.length > 0) {
            this.items = this.items.filter(item => !ids.includes(item.id));
        }
    }
}
