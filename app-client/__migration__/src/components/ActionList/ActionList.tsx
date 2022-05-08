import Box from '@mui/material/Box';

type ActionListProps = {
    actions?: Array<{
        name: string;
        desc: string;
    }>;
};

const ActionList = ({ actions = [] }: ActionListProps) => {
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
