import Typography, { TypographyProps } from '@mui/material/Typography';

const Copyright = (props: TypographyProps) => (
    <Typography variant="body2" color="text.secondary" align="center" {...props}>
        {'Copyright © '}
        <span color="inherit">
                Maksim Buryshynets
            </span>{' '}
        {new Date().getFullYear()}
        {'.'}
    </Typography>
);

export default Copyright;
