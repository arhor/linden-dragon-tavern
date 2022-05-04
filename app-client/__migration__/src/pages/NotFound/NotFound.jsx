import React from 'react';

import StatelessWidget, { SIZE, TYPE } from '@/components/StatelessWidget';

function NotFound() {
    return (
        <StatelessWidget
            type={TYPE.PAGE}
            size={SIZE.LARGE}
            title="Ups, page not found..."
            description="Please, try to find somewhere else :)"
        />
    );
}

export default NotFound;
