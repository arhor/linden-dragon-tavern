import { action, makeObservable, observable } from 'mobx';

import { generateUUID } from '@/utils/coreUtils';

type NotificationItem = {
    id?: string;
    message: string;
    level: 'default' | 'error' | 'success' | 'warning' | 'info';
};

export default class NotificationStore {
    items: NotificationItem[] = [];

    constructor() {
        makeObservable(this, {
            items: observable,
            enqueue: action.bound,
            remove: action.bound,
        });
    }

    enqueue(item: NotificationItem) {
        this.items.push({
            id: generateUUID(),
            ...item,
        });
    }

    remove(...ids: string[]) {
        if (ids?.length > 0) {
            this.items = this.items.filter(item => !ids.includes(item.id!));
        }
    }
}
