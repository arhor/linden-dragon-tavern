import React from 'react';

import Box from '@mui/material/Box';

const ActionList: React.FC<{
    actions?: Array<{ name: string, desc: string }>;
}> = ({ actions = [] }) => {
    const lastActionIndex = actions.length - 1;
    return (
        <div data-testid="action-list-container">
            {actions.map(({ name, desc }, i) => (
                <Box key={name}>
                    <b>{name}</b>{`: ${desc}${i < lastActionIndex ? ', ' : ''}`}
                </Box>
            ))}
        </div>
    );
};

export default ActionList;
