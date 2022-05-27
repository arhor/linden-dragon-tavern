import { observer } from 'mobx-react';

import Box from '@mui/material/Box';
import LinearProgress from '@mui/material/LinearProgress';

import CreatureList from '@/components/CreatureList';
import { useStore } from '@/store';

const Creatures = () => {
    const { creature } = useStore();

    return (
        <Box
            sx={{
                marginTop: 8,
                flexDirection: 'column',
                alignItems: 'center',
                height: 400,
            }}
        >
            <CreatureList items={creature.items} />
            {creature.loading && <LinearProgress />}
        </Box>
    );
};

export default observer(Creatures);
