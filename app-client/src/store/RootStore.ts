import AbilityStore from '@/store/ability';
import AppStore from '@/store/app';
import CreatureStore from "@/store/creature";
import NotificationStore from '@/store/notification';
import SpellStore from '@/store/spell';
import UserStore from '@/store/user';

export type RootStore = {
    ability: AbilityStore;
    app: AppStore;
    creature: CreatureStore;
    notification: NotificationStore;
    spell: SpellStore;
    user: UserStore;
};

export const createStore = (): RootStore => ({
    ability: new AbilityStore(),
    app: new AppStore(),
    creature: new CreatureStore(),
    notification: new NotificationStore(),
    spell: new SpellStore,
    user: new UserStore(),
});
