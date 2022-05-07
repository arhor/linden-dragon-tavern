import React from 'react';

import PropTypes from 'prop-types';

import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';

process.env.NODE_ENV !== 'production' ? CreatureDetails.propTypes = {
    creature: PropTypes.object.isRequired,
} : void 0;

function CreatureDetails({ creature }) {
    
    return (
        <Card>
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                    {creature.name}
                </Typography>
            </CardContent>
        </Card>
    );
}

export default CreatureDetails;
