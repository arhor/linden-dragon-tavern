import { createLocalVue, shallowMount } from '@vue/test-utils';
import Vue from 'vue';
import Vuetify from 'vuetify';
import DndCommaSeparatedList from '@/components/DndCommaSeparatedList.vue';

describe('DndCommaSeparatedList.vue', () => {
    Vue.use(Vuetify);

    let localVue;
    beforeEach(() => {
        localVue = createLocalVue();
        localVue.use(Vuetify);
    });

    test('renders props.title and props.items when passed', () => {
        const title = 'test title';
        const items = ['one', 'two', 'three'];

        const wrapper = shallowMount(DndCommaSeparatedList, {
            localVue,
            propsData: { title, items },
        });

        expect(wrapper.text()).toMatch(`${title}: ${items.join(', ')}`);
    });
});
