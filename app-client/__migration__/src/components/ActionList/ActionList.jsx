import React from 'react';
import PropTypes from 'prop-types';
import Box from '@mui/material/Box';

ActionList.propTypes = {
    actions: PropTypes.arrayOf(PropTypes.shape({
        name: PropTypes.string.isRequired,
        desc: PropTypes.string.isRequired,
    })),
};

function ActionList({ actions = [] }) {
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
}

export default ActionList;
