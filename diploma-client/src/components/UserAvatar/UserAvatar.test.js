import React from 'react';
import ReactDOM from 'react-dom';
import UserAvatar from '@/components/UserAvatar';

describe('UserAvatar tests', () => {
    test('Should render without crashing', () => {
        const div = document.createElement('div');

        ReactDOM.render(<UserAvatar user={{}} />, div);

        ReactDOM.unmountComponentAtNode(div);
    });
});
