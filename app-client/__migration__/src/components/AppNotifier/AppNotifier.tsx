import React, { useEffect } from 'react';

import { autorun } from 'mobx';
import { observer } from 'mobx-react';
import { useSnackbar } from 'notistack';

import { useStore } from '@/store';

const AppNotifier: React.FC = () => {
    const { notification } = useStore();
    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => autorun(() => {
        const displayed: string[] = [];
        notification.items.forEach(({ id, level, message }) => {
            if (!displayed.includes(id!)) {
                enqueueSnackbar(message, { variant: level });
                displayed.push(id!);
            }
        });
        notification.remove(...displayed);
    }), []);
    return null;
};

export default observer(AppNotifier);
