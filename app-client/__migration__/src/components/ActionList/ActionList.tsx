import Box from '@mui/material/Box';

export type Props = {
    actions?: Array<{
        name: string;
        desc: string;
    }>;
};

const ActionList = ({ actions = [] }: Props) => {
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
