import Vue from 'vue';
import Vuetify from 'vuetify';
import {createLocalVue, shallowMount} from '@vue/test-utils';

import DndCommaSeparatedList from '@/components/DndCommaSeparatedList.vue';

describe('dnd-comma-separated-list component', () => {
    let localVue;

    beforeAll(() => {
        Vue.use(Vuetify);
        localVue = createLocalVue();
        localVue.use(Vuetify);
    });

    test('should render passed props: `title` and `items`', () => {
        // given
        const title = 'test title';
        const items = ['one', 'two', 'three'];

        // when
        const wrapper = shallowMount(DndCommaSeparatedList, {
            localVue,
            propsData: { title, items },
        });

        // then
        expect(wrapper.text()).toEqual(`${title}: ${items.join(', ')}`);
    });

    test('should render passed props: `items` without any title', () => {
        // given
        const items = ['one', 'two', 'three'];

        // when
        const wrapper = shallowMount(DndCommaSeparatedList, {
            localVue,
            propsData: { items },
        });

        // then
        expect(wrapper.text()).toEqual(`${items.join(', ')}`);
    });
});
