import Vue from 'vue';
import Vuetify from 'vuetify';
import { createLocalVue, shallowMount } from '@vue/test-utils';

import ActionList from '@/components/ActionList';

describe('ActionList.vue', () => {
    let localVue;

    beforeAll(() => {
        Vue.use(Vuetify);
        localVue = createLocalVue();
        localVue.use(Vuetify);
    });

    test('should render component without errors', () => {
        // given
        const actions = [
            { name: 'One', desc: 'Description 1' },
            { name: 'Two', desc: 'Description 2' },
            { name: 'Three', desc: 'Description 3' },
        ];

        // when
        const wrapper = shallowMount(ActionList, {
            localVue,
            propsData: { actions },
        });

        // then
        const actual = wrapper.text().replace(/\s{2,}/g, ' ');
        const expected = actions.map(({ name, desc }) => `${name} : ${desc}`).join(' ');

        expect(actual).toMatch(expected);
    });
});
