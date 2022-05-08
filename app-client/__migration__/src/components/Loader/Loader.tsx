import React from 'react';

import Box from '@mui/material/Box';
import CircularProgress from '@mui/material/CircularProgress';

const Loader: React.FC = () => (
    <Box sx={{ display: 'flex' }}>
        <CircularProgress />
    </Box>
);

export default Loader;
