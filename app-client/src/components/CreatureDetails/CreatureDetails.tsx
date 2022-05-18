import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';

export type Props = {
    creature: {
        name: string;
    };
};

const CreatureDetails = ({ creature }: Props) => (
    <Card>
        <CardContent>
            <Typography gutterBottom variant="h5" component="div">
                {creature.name}
            </Typography>
        </CardContent>
    </Card>
);

export default CreatureDetails;
