import React from 'react';

import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import PropTypes from 'prop-types';

CreatureDetails.propTypes = {
    creature: PropTypes.object.isRequired,
};

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
