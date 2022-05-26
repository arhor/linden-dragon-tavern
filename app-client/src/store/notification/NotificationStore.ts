import { action, makeObservable, observable } from 'mobx';

import { generateUUID, Optional } from '@/utils/core-utils';

type Notification = {
    message: string;
    level: 'default' | 'error' | 'success' | 'warning' | 'info';
    timeout?: Optional<number>,
};

type NotificationId = string | number;

export default class NotificationStore {

    items: (Notification & { id: NotificationId })[] = [];

    constructor() {
        makeObservable(this, {
            items: observable,
            enqueue: action.bound,
            remove: action.bound,
        });
    }

    enqueue(item: Notification) {
        this.items.push({
            id: generateUUID(),
            ...item,
        });
    }

    remove(ids: NotificationId[]) {
        if (ids.length > 0) {
            this.items = this.items.filter(item => !ids.includes(item.id));
        }
    }
}
