import { createLocalVue, shallowMount } from '@vue/test-utils';
import Vue from 'vue';
import Vuetify from 'vuetify';
import DndCommaSeparatedList from '@/components/DndCommaSeparatedList.vue';

describe('DndCommaSeparatedList.vue', () => {
    let localVue;

    beforeAll(() => {
        Vue.use(Vuetify);
        localVue = createLocalVue();
        localVue.use(Vuetify);
    });

    test('should render passed props: `title` and `items`', () => {
        const title = 'test title';
        const items = ['one', 'two', 'three'];

        const wrapper = shallowMount(DndCommaSeparatedList, {
            localVue,
            propsData: { title, items },
        });

        expect(wrapper.text()).toEqual(`${title}: ${items.join(', ')}`);
    });

    test('should render passed props: `items` without any title', () => {
        const items = ['one', 'two', 'three'];

        const wrapper = shallowMount(DndCommaSeparatedList, {
            localVue,
            propsData: { items },
        });

        expect(wrapper.text()).toEqual(`${items.join(', ')}`);
    });
});
