import { useEffect } from 'react';

import { autorun } from 'mobx';
import { observer } from 'mobx-react';
import { useSnackbar } from 'notistack';

import { useStore } from '@/store';

const AppNotifier = () => {
    const { notification } = useStore();
    const { enqueueSnackbar } = useSnackbar();

    useEffect(
        () => autorun(() => {
            const enqueued = notification.items.map((item) => enqueueSnackbar(item.message, {
                key: item.id,
                variant: item.level,
                autoHideDuration: item.timeout,
            }));
            notification.remove(enqueued);
        }),
        []
    );
    return null;
};

export default observer(AppNotifier);
