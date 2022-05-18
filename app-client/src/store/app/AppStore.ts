import { makeObservable } from 'mobx';

export default class AppStore {
    constructor() {
        makeObservable(this, {}, { autoBind: true });
    }
}
